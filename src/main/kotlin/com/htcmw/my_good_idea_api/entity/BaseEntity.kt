package com.htcmw.my_good_idea_api.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant


@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity(

    @CreationTimestamp
    @Column(updatable = false)
    var createdAt: Instant? = null,

    @CreatedBy
    @Column(nullable = false)
    var createdBy: String? = null,

    @UpdateTimestamp
    @Column(nullable = true, insertable = false)
    var updatedAt: Instant? = null,

    @LastModifiedBy
    @Column(nullable = true, insertable = false)
    var updatedBy: String? = null

)