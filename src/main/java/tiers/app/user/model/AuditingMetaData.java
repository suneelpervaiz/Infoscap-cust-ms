package tiers.app.user.model;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.Instant;


@MappedSuperclass
@Getter
@Setter
public class AuditingMetaData{

    private boolean isActive;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;
    private Instant deletedAt;
    private String deletedBy;

}
