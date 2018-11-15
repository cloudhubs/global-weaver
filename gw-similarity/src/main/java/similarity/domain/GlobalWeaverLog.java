package similarity.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class GlobalWeaverLog {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Timestamp time;

    @Column(length = 10, name = "owner")
    private String owner;

    @Column(length = 45, name = "server_type")
    private String serverType;




}
