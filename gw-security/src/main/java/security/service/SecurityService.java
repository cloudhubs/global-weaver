package security.service;

import edu.baylor.ecs.seer.common.domain.LocalWeaverResult;
import edu.baylor.ecs.seer.common.domain.Node;
import edu.baylor.ecs.seer.common.domain.SecurityData;

import java.util.List;
import java.util.Set;

/**
 * This interface defines the interactions supported by the SecurityService. It includes a getSecurityData() method
 * which allows a user to retrieve information about module security based on a given role definition string. It also
 * includes several helper functions to help build role trees, validate edges between roles, and process individual
 * LocalWeaverResult objects.
 */
public interface SecurityService {

    /**
     * This will return all violations of the given security role relationship string present in the modules
     * defined in the YAML config file for this module.
     *
     * @param roleDef The role relationship definition -- it must follow a specific pattern as outlined in
     *                createRoleTree()
     * @return The object containing the data about all viewed modules and their security violations
     * @throws Exception Any exceptions thrown; to be handled by the SecurityExceptionAspect
     */
    SecurityData getSecurityData(String roleDef) throws Exception;

    /**
     * This will process the given LocalWeaverResult based on the given Node tree and return a string containing
     * all of the violations of the role tree that occur in the local weaver result.
     *
     * @param roleTree The Node tree against which the local weaver result will be checked
     * @param result The LocalWeaverResult to be checked
     * @return A string containing security violation information
     * @throws Exception Any exceptions thrown; to be handled by the SecurityExceptionAspect
     */
    String processLocalWeaverResult(Node roleTree, LocalWeaverResult result) throws Exception;

    /**
     * This will use a depth-first search to build a list of edges present in the local weaver data being analyzed.
     * An edge is defined as a call from one method to another, and includes information about the roles permitted
     * to access each method. It is defined as a two-element list of strings. This set is the set which will be
     * examined by the validateEdge() function.
     *
     * @return A set of all edges present in the data being analyzed
     */
    Set<List<String>> DFSBuildEdges();

    /**
     * This will check the a pair of roles, one associated with the start of an edge and the other associated with
     * the end of an edge, to see if the start role is higher or same access level as the end role based on the given
     * role tree. It will return true if it is, and false otherwise. This allows each edge between methods (see
     * documentation for DFSBuildEdges()) to be validated, as if any pair of roles from the start to the end fails
     * to validate, this indicates that a method tries to call a method for which it needs higher permissions; thus,
     * security is violated.
     *
     * @param start The starting role
     * @param end The ending role
     * @param roleTree The role tree against which the edge will be checked
     * @return Whether the edge is valid
     */
    String validateEdge(String start, String end, Node roleTree);

    /**
     * This will create a role tree based on the given role definition string. The string must follow a specific
     * format, starting with a single role name, then with each subsequent line (delimited by a newline) containing
     * a single edge which has one of two formats: (1) startRole -> endRole (2) startRole <-> endRole. The first
     * format indicates an edge where the startRole has a higher permission than the endRole, while second indicates
     * that the two roles are on the same level. In tree syntax, the first indicates that the endRole is a child of
     * the startRole, while the second indicates that each is a child of the other. Additionally, a role can
     * only appear on the left hand side of an edge if it has previously appeared on the right hand side of an edge
     * (with the exception of the first line). Below is an example:
     *
     * Admin
     * Admin -> Moderator
     * Admin -> User
     * Moderator -> SubModerator
     * Admin <-> Reviewer
     *
     * @param roleDef The role relationship definition string.
     * @return The created role tree
     */
    Node createRoleTree(String roleDef);
}
