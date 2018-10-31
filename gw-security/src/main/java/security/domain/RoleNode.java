package security.domain;

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

    }

}
