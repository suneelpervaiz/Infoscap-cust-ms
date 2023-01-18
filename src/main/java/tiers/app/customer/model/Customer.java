package tiers.app.customer.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends AuditingMetaData{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean isApproved;
    private String approvedBy;
    private Timestamp approvedAt;
    private String phoneNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    @JsonBackReference
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "language_id", referencedColumnName = "id")
    private Language language;
}
