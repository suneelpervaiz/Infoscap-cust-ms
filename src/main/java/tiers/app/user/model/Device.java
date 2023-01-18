package tiers.app.user.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tiers.app.user.model.Enum.DeviceType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
public class Device extends AuditingMetaData{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String physicalDeviceUniqueId;
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;
    private boolean isDeviceUsedOnCreateAccount;
    private boolean isDeviceAuthorizedByUser;
    private String deviceName;
    @ManyToMany(mappedBy = "userDevices")
    private Set<User> deviceUsers = new HashSet<>();
}
