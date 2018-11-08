package security.service;

import security.domain.LocalWeaverResult;
import security.domain.RoleNode;

import java.util.List;
import java.util.Set;

public interface SecurityService {

    String getSecurityData(String roleDef) throws Exception;

    String processLocalWeaverResult(RoleNode roleTree, LocalWeaverResult result) throws Exception;

    Set<List<String>> DFSBuildEdges();

    String validateEdge(String start, String end, RoleNode roleTree);

    RoleNode createRoleTree(String roleDef);
}
