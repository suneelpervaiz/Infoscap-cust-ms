package tiers.app.customer.model;

import lombok.*;
import tiers.app.customer.model.Enum.DeviceType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Device extends AuditingMetaData{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String physicalUniqueId;
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;
    private boolean isDeviceUsedOnCreateAccount;
    private String deviceAuthorizedByUser;
    private String deviceName;
    @ManyToMany(mappedBy = "userDevices")
    private Set<User> deviceUsers = new HashSet<>();
}
