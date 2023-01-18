package tiers.app.customer.model;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;
import java.util.Date;


@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class AuditingMetaData{

    private boolean isActive;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @CreatedBy
    @Column(nullable = true, updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(nullable = false)
    private Date updatedAt;

    @LastModifiedBy
    @Column(nullable = true)
    private String updatedBy;


    private Date deletedAt;
    private String deletedBy;

}
