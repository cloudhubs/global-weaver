package security.domain;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//ToDo: class description, method description
public class RoleNode {

    private String role;
    private ArrayList<RoleNode> children;

    public RoleNode(String role) {
        this.role = role;
        this.children = new ArrayList<>();
    }

    public void search(String role, List<RoleNode> visited, List<RoleNode> matches) {
        if (visited.contains(this)) {
            return;
        }
        visited.add(this);
        if (this.role.equals(role)) {
            matches.add(this);
        } else if (!this.children.isEmpty()) {
            for ( RoleNode child : this.children ) {
                child.search(role,visited, matches);
            }
        }
    }

    public boolean insert(String role, String beneath) {
        List<RoleNode> matches = new ArrayList<>();
        this.search(role, new ArrayList<>(), matches);
        if (matches.size() > 0) {
            RoleNode node = matches.get(0);
            matches.clear();
            this.search(beneath, new ArrayList<>(), matches);
            matches.get(0).children.add(node);
            return true;
        }
        this.search(beneath, new ArrayList<>(), matches);
        if (matches.size() == 0) {
            return false;
        }
        matches.get(0).children.add(new RoleNode(role));

        return true;
    }

    public RoleNode subTree(String role) {
        List<RoleNode> matches = new ArrayList<>();
        this.search(role, new ArrayList<>(), matches);
        if (matches.size() > 0) {
            return matches.get(0);
        }
        return null;
    }

    public boolean contains(String role) {
        List<RoleNode> matches = new ArrayList<>();
        this.search(role, new ArrayList<>(), matches);
        return !matches.isEmpty();
    }

    public boolean childContains(String role) {
        List<RoleNode> matches = new ArrayList<>();
        for ( RoleNode child : this.children ) {
            this.search(role, new ArrayList<>(), matches);
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
}
