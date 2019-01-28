package security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.baylor.ecs.seer.common.domain.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import security.config.YAMLConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    SecurityServiceImpl securityService;

    @Autowired
    YAMLConfig config;

    @Autowired
    private RestTemplate restTemplate;

    private Map<String, List<String>> roles = null;
    private Map<String, List<String>> nodes = null;

    //ToDo: description
    @Override
    public SecurityData getSecurityData(String roleDef) throws Exception {
        HarvesterData data;
        StringBuilder output = new StringBuilder();

        ResponseEntity<HarvesterData> response = restTemplate.exchange(
                config.getUrl() + ":" + config.getServers().get(0) + config.getEndpoints().get(0),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<HarvesterData>(){});
        data = response.getBody();

        ArrayList<LocalWeaverResult> results = data.getData();

        Node roleTree = securityService.createRoleTree(roleDef);

        for ( LocalWeaverResult entry : results ) {
            String str = securityService.processLocalWeaverResult(roleTree, entry);
            output.append(str);
        }

        SecurityData ret = new SecurityData();
        ret.setStatus(200);
        ret.setMessage("OK");
        ret.setData(output.toString());

        return ret;
    }

    public String processLocalWeaverResult(Node roleTree, LocalWeaverResult entry) throws Exception {
        if (entry.getType() != LocalWeaverResultType.SECURITY) {
            return "";
        }

        StringBuilder output = new StringBuilder();

        String json = entry.getData();

        String[] jsons = json.split("@");

        roles = new ObjectMapper().readValue(jsons[0], Map.class);
        nodes = new ObjectMapper().readValue(jsons[1], Map.class);

        Set<List<String>> edgeSet = securityService.DFSBuildEdges();

        for (List<String> e : edgeSet) {
            output.append(securityService.validateEdge(e.get(0), e.get(1), roleTree));
        }

        return output.toString();
    }

    public Set<List<String>> DFSBuildEdges() {
        Stack<String> stack = new Stack<>();
        Set<List<String>> edgeSet = new HashSet<>();

        for (String node : nodes.keySet()) {
            stack.push(node);
            List<String> visited = new ArrayList<>();
            while (!stack.empty()) {
                String cur = stack.pop();
                visited.add(node);
                if (nodes.get(cur) != null) {
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
        }
        return edgeSet;
    }
    //ToDo: when database ready, modify to persist to DB
    public String validateEdge(String start, String end, Node roleTree) {
        StringBuilder output = new StringBuilder();
        if (roles.get(start) != null) {
            for (String srole : roles.get(start)) {
                if (roles.get(end) != null) {
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
                }
            }
        }
        return output.toString();
    }

    //ToDo: when database ready, modify to persist to DB
    public Node createRoleTree(String roleDef) {
        String[] lines = roleDef.split("\n");
        if (lines[0].contains("->")) {
            System.out.println("ERROR! Line 0 should not be an edge!");
            return null;
        }

        Node roleTree = new Node(lines[0].replaceAll(" ", ""));

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
