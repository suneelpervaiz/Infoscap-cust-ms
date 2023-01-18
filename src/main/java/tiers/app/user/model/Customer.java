package tiers.app.user.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
public class Customer extends AuditingMetaData{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean isApproved;
    private String approvedBy;
    private Timestamp approvedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "language_id", referencedColumnName = "id")
    private Language language;
    private Boolean isPrimaryLanguage;

}
