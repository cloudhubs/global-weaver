package security.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This is a recursive tree structure used to store a Role hierarchy for the Security module. Each node contains
 * a string defining its Role and a list of children that has no bounds on its size. The tree structure allows for a
 * child to have its parent as one of its children (this is necessary for Roles that inherit each other's permissions).
 */
public class RoleNode {

    /**
     * This is the node's Role.
     */
    private String role;
    /**
     * This is the node's children. It may include one or more parents of the child.
     */
    private ArrayList<RoleNode> children;

    public RoleNode(String role) {
        this.role = role;
        this.children = new ArrayList<>();
    }

    /**
     * This will search the tree defined by this node for a node with the given Role and return it.
     *
     * @param role This is the Role for which to search.
     */
    public RoleNode search(String role) {
        List<RoleNode> visited = new ArrayList<>();
        List<RoleNode> matches = new ArrayList<>();

        // make sure we don't search this node again
        visited.add(this);

        // recursion
        doSearch(role, visited, matches);

        // return null if no match, otherwise return the first node in the list (should be the only one)
        return matches.isEmpty() ? null : matches.get(0);
    }

    /**
     * This is an auxiliary internal search function to prevent the user from being required to pass multiple
     * empty collections. Otherwise, its functionality is nearly identical to search(String).
     *
     * @param role The Role for which to search
     * @param visited A list of visited nodes to prevent infinite recursion
     * @param matches A list of matches
     */
    private void searchAux(String role, List<RoleNode> visited, List<RoleNode> matches) {
        if (visited.contains(this)) {
            return;
        }

        visited.add(this);
        doSearch(role, visited, matches);
    }

    /**
     * This will perform the actual check of adding the current node if it matches the role and recursing to
     * all unvisited children.
     *
     * @param role The Role for which to search
     * @param visited A list of all visited nodes to prevent infinite search loops
     * @param matches A list of all current matches
     */
    private void doSearch(String role, List<RoleNode> visited, List<RoleNode> matches) {
        if (this.role.equals(role)) {
            matches.add(this);
        } else if (!this.children.isEmpty()) {
            for ( RoleNode child : this.children ) {
                child.searchAux(role, visited, matches);
            }
        }
    }

    /**
     * This will insert a new node with the given Role beneath the node with value
     *
     * @param role
     * @param beneath
     * @return
     */
    public boolean insert(String role, String beneath) {
        RoleNode match = new ArrayList<>();
        match = this.search(role);
        if (match != null) {
            RoleNode node = match;
            match = this.search(beneath);
            if (match == null) {
                return false;
            }
            match.children.add(node);
            return true;
        }
        match = this.search(beneath);
        if (match == null) {
            return false;
        }
        match.children.add(new RoleNode(role));

        return true;
    }

    public RoleNode subTree(String role) {
        RoleNode match = new ArrayList<>();
        match = this.search(role);
        if (match != null) {
            return match;
        }
        return null;
    }

    public boolean contains(String role) {
        List<RoleNode> matches = new ArrayList<>();
        matches = this.search(role);
        return !matches.isEmpty();
    }

    public boolean childContains(String role) {
        List<RoleNode> matches = new ArrayList<>();
        for ( RoleNode child : this.children ) {
            matches.addAll(this.search(role));
        }
        return !matches.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleNode roleNode = (RoleNode) o;
        return Objects.equals(role, roleNode.role) &&
                Objects.equals(children, roleNode.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, children);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("RoleNode:\nRole: ").append(this.role).append("\n");
        for (RoleNode child : this.children) {
            str.append("Child Role: ").append(child.role).append("\n");
        }
        return str.toString();
    }
}
