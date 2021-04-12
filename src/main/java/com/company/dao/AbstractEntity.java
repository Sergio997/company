package com.company.dao;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id;

    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(name = "reference")
    private UUID reference;

    @Getter
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private Date createdDate;

    @Getter
    @Column(name = "modified_date")
    @LastModifiedDate
    private Date modifiedDate;

    @PrePersist
    public void prePersist() {
        createdDate = new Date();
        if(Objects.isNull(reference)){
            reference = UUID.randomUUID();
        }
    }

    @PreUpdate
    public void preUpdate() {
        modifiedDate = new Date();
    }

}
