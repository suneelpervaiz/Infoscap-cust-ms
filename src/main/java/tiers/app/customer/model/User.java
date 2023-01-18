package tiers.app.customer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends AuditingMetaData{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    @Email(message = "Alert : Email has already been used in another account. Please use sign in")
    private String userName;

    @NotBlank(message = "Password required")
    private String password;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "userDevice",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "deviceId")
    )
    private Set<Device> userDevices = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Set<Customer> customers = new HashSet<>();


    /**************************************/
    /*          For Spring security       */
    /**************************************/

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

}
