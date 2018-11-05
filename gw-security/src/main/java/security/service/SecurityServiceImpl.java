package security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import security.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private RestTemplate restTemplate;

    private Map<String, List<String>> roles = null;
    private Map<String, List<String>> nodes = null;

    @Override
    public String getSecurityData() {
        HarvesterData data;
        StringBuilder output = new StringBuilder();

        data = restTemplate.getForObject(
                "http://localhost:7002/security",
                HarvesterData.class);

        ArrayList<LocalWeaverResult> results = data.getData();

        /*/ FOR TESTING: Create example role tree //

        RoleNode roleTree = new RoleNode("Admin");
        roleTree.insert("User", "Admin");

        // END TESTING /*/

        //*/ FOR TRAIN-TICKET TESTING: Create example role tree //

        RoleNode roleTree = new RoleNode("SuperAdmin");
        roleTree.insert("Admin", "SuperAdmin");
        roleTree.insert("Reviewer", "SuperAdmin");
        roleTree.insert("User", "Admin");
        roleTree.insert("Guest", "User");
        roleTree.insert("Moderator", "Admin");
        roleTree.insert("Moderator", "Reviewer");
        roleTree.insert("Reviewer", "Moderator");

        // END TESTING /*/

        for ( LocalWeaverResult entry : results ) {
            //if (entry.getType() == LocalWeaverResultType.SECURITY) {

                output.append("Now processing Module " + entry.getModuleId() + " - " + entry.getModuleName() + ":\n");

                //String json = entry.getData();

                // Testing only
                String json = "{\"seer.ecs.baylor.edu.securitytestsimple.UserAccessible.UserMethodBad1()\":[\"User\",\"Admin\"],\"seer.ecs.baylor.edu.securitytestsimple.UserAccessible.UserMethod1()\":[\"User\",\"Admin\"],\"seer.ecs.baylor.edu.securitytestsimple.AdminAccessible.AdminMethod2()\":[\"Admin\"],\"seer.ecs.baylor.edu.securitytestsimple.AdminAccessible.AdminMethod1()\":[\"Admin\"]} @{\"seer.ecs.baylor.edu.securitytestsimple.UserAccessible.UserMethodBad1()\":[\"seer.ecs.baylor.edu.securitytestsimple.AdminAccessible.AdminMethod1()\"],\"seer.ecs.baylor.edu.securitytestsimple.UserAccessible.UserMethod1()\":[],\"seer.ecs.baylor.edu.securitytestsimple.AdminAccessible.AdminMethod2()\":[\"seer.ecs.baylor.edu.securitytestsimple.UserAccessible.UserMethod1()\"],\"seer.ecs.baylor.edu.securitytestsimple.AdminAccessible.AdminMethod1()\":[]}";

                String[] jsons = json.split("@");

                try {
                    roles = new ObjectMapper().readValue(jsons[0], Map.class);
                    nodes = new ObjectMapper().readValue(jsons[1], Map.class);
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }

                Stack<String> stack = new Stack<>();
                Set<List<String>> edgeSet = new HashSet<>();

                for (String node : nodes.keySet()) {
                    stack.push(node);
                    List<String> visited = new ArrayList<>();
                    while (!stack.empty()) {
                        String cur = stack.pop();
                        visited.add(node);
                        for (String adj : nodes.get(cur)) {
                            if (!visited.contains(adj)) {
                                stack.push(adj);
                            }
                            List<String> edge = new ArrayList<>();
                            edge.add(cur);
                            edge.add(adj);
                            edgeSet.add(edge);
                        }
                    }
                }

                for (List<String> e : edgeSet) {
                    output.append(validateEdge(e.get(0), e.get(1), roleTree));
                }
                output.append("Done processing Module " + entry.getModuleId() + " - " + entry.getModuleName() + "!\n\n");
            //}
        }

        return output.toString();
    }

    private String validateEdge(String start, String end, RoleNode roleTree) {
        StringBuilder output = new StringBuilder();
        for ( String srole : roles.get(start) ) {
            for ( String erole : roles.get(end) ) {
                if (roleTree.subTree(erole).childContains(srole)
                    && !roleTree.subTree(srole).childContains(erole)) {
                    output.append("Error! Edge from ")
                            .append(start)
                            .append(" to ")
                            .append(end)
                            .append(" is invalid!\nThis is caused by role mismatch between ")
                            .append(srole)
                            .append(" and ")
                            .append(erole)
                            .append(".\n");
                }
            }
        }
        return output.toString();
    }

}
