package security.domain;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

public class RoleNode {

    private String role;
    private ArrayList<RoleNode> children;

    public RoleNode(String role) {
        this.role = role;
        this.children = new ArrayList<>();
    }

    public void search(String role, List<RoleNode> matches) {
        if (this.role.equals(role)) {
            matches.add(this);
        } else if (!this.children.isEmpty()) {
            for ( RoleNode child : this.children ) {
                child.search(role, matches);
            }
        }
    }

    public void insert(String role, String beneath) {
        List<RoleNode> matches = new ArrayList<>();
        this.search(role, matches);
        if (matches.size() > 0) {
            RoleNode node = matches.get(0);
            matches.clear();
            this.search(beneath, matches);
            matches.get(0).children.add(node);
            return;
        }
        this.search(beneath, matches);
        matches.get(0).children.add(new RoleNode(role));
    }

    public RoleNode subTree(String role) {
        List<RoleNode> matches = new ArrayList<>();
        this.search(role, matches);
        if (matches.size() > 0) {
            return matches.get(0);
        }
        return null;
    }

    public boolean contains(String role) {
        List<RoleNode> matches = new ArrayList<>();
        this.search(role, matches);
        return !matches.isEmpty();
    }

    public boolean childContains(String role) {
        List<RoleNode> matches = new ArrayList<>();
        for ( RoleNode child : this.children ) {
            this.search(role, matches);
        }
        return !matches.isEmpty();
    }

}
