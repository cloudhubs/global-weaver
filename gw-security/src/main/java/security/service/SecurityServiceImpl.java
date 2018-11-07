package security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import security.config.YAMLConfig;
import security.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    YAMLConfig config;

    @Autowired
    private RestTemplate restTemplate;

    private Map<String, List<String>> roles = null;
    private Map<String, List<String>> nodes = null;

    @Override
    public String getSecurityData(String roleDef) {
        HarvesterData data;
        StringBuilder output = new StringBuilder();

        //ResponseEntity<HarvesterData> response = restTemplate.exchange(
        //        "http://localhost:" + config.getServers().get(0) + "/security",
        //        HttpMethod.GET,
        //        null,
        //        new ParameterizedTypeReference<HarvesterData>(){});
        //data = response.getBody();

        data = restTemplate.getForObject("http://localhost:" + config.getServers().get(0) + "/security",
                HarvesterData.class);

        ArrayList<LocalWeaverResult> results = data.getData();

        RoleNode roleTree = createRoleTree(roleDef);

        for ( LocalWeaverResult entry : results ) {
            if (entry.getType() == LocalWeaverResultType.SECURITY) {

                output.append("Now processing Module " + entry.getModuleId() + " - " + entry.getModuleName() + ":\n");

                String json = entry.getData();

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
                        try {
                            for (String adj : nodes.get(cur)) {
                                if (!visited.contains(adj)) {
                                    stack.push(adj);
                                }
                                List<String> edge = new ArrayList<>();
                                edge.add(cur);
                                edge.add(adj);
                                edgeSet.add(edge);
                            }
                        } catch (Exception ex) {
                            System.out.println(ex.toString());
                        }
                    }
                }

                for (List<String> e : edgeSet) {
                    output.append(validateEdge(e.get(0), e.get(1), roleTree));
                }

                output.append("Done processing Module " + entry.getModuleId() + " - " + entry.getModuleName() + "!\n\n");
            }
        }

        return output.toString();
    }

    private String validateEdge(String start, String end, RoleNode roleTree) {
        StringBuilder output = new StringBuilder();
        for ( String srole : roles.get(start) ) {
            try {
                for (String erole : roles.get(end)) {
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
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }
        return output.toString();
    }

    private RoleNode createRoleTree(String roleDef) {
        String[] lines = roleDef.split("\n");
        if (lines[0].contains("->")) {
            System.out.println("ERROR! Line 0 should not be an edge!");
            return null;
        }

        RoleNode roleTree = new RoleNode(lines[0].replaceAll(" ", ""));

        for ( int i = 1; i < lines.length; i++ ) {
            String line = lines[i].replaceAll(" ", "");
            String[] split = line.split("->");
            if (split.length != 2) {
                System.out.println("ERROR! Bad input line on line " + i + "!");
                return null;
            }
            if (split[0].endsWith("<")) {
                String first = split[0].substring(0, split[0].length() - 1);
                if (!roleTree.insert(split[1], first)) {
                    System.out.println("ERROR! Bad input on line " + i + "!\n" +
                            "Left hand side of edge must appear earlier as right hand side!");
                    return null;
                }
                if (!roleTree.insert(first, split[1])) {
                    System.out.println("ERROR! Bad input on line " + i + "!\n" +
                            "Left hand side of edge must appear earlier as right hand side!");
                    return null;
                }
            } else {
                if (!roleTree.insert(split[1], split[0])) {
                    System.out.println("ERROR! Bad input on line " + i + "!\n" +
                            "Left hand side of edge must appear earlier as right hand side!");
                    return null;
                }
            }
        }

        return roleTree;
    }

}
