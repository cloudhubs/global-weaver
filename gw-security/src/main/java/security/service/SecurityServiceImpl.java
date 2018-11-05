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
    public void getSecurityData() {
        HarvesterData data;

        data = restTemplate.getForObject(
                "http://localhost:7002/security",
                HarvesterData.class);

        ArrayList<LocalWeaverResult> results = data.getData();

        // FOR TESTING: Create example role tree
        RoleNode roleTree = new RoleNode("Admin");
        roleTree.insert("User", "Admin");

        // END TESTING

        for ( LocalWeaverResult entry : results ) {
            //if (entry.getType() == LocalWeaverResultType.SECURITY) {

                System.out.println("Now processing Module " + entry.getModuleId() + " - " + entry.getModuleName() + ":");

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
                    validateEdge(e.get(0), e.get(1), roleTree);
                }
                System.out.println("Done processing!\n");
            //}
        }
    }

    private void validateEdge(String start, String end, RoleNode roleTree) {
        for ( String srole : roles.get(start) ) {
            for ( String erole : roles.get(end) ) {
                RoleNode s = roleTree.subTree(srole);
                RoleNode e = roleTree.subTree(erole);
                boolean inS = s.childContains(erole);
                boolean inE = e.childContains(srole);
                if (roleTree.subTree(erole).childContains(srole)
                    && !roleTree.subTree(srole).childContains(erole)) {
                    System.out.printf("Error! Edge from %s to %s is invalid!\n", start, end);
                    System.out.printf("This is caused by role mismatch between %s and %s.\n", srole, erole);
                }
            }
        }
    }

}
