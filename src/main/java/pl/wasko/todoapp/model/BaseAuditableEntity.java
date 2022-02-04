package pl.wasko.todoapp.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Getter
@Setter
@Embeddable
public class BaseAuditableEntity {

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    @PrePersist
    void prePersist(){
        createdOn=LocalDateTime.now();
    }

    @PreUpdate
    void preMarge(){
        updatedOn=LocalDateTime.now();
    }
}
