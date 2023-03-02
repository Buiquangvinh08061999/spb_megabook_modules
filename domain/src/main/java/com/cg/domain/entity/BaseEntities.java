package com.cg.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;


@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntities {

    @Column(columnDefinition = "boolean default false")
    private boolean deleted = false;

    @CreationTimestamp
    @Column(name = "create_at")
    private Date createAt;

    @CreatedBy
    @Column(name = "create_by")
    private String createBy;

    @UpdateTimestamp
    @Column(name = "update_at")
    private Date updateAt;

    @LastModifiedBy
    @Column(name = "update_by")
    private String updateBy;

}
