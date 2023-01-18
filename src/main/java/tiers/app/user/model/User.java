package tiers.app.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends AuditingMetaData{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    @ManyToMany
    @JoinTable(
            name = "userDevice",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "deviceId")
    )
    private Set<Device> userDevices = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Customer> customers = new HashSet<>();


    /**************************************/
    /*          For Spring security       */
    /**************************************/

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

}
