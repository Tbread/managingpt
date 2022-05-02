package com.healthcare.managingpt.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class TimeStamped (

    @CreatedDate
    private val createdAt:LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    private var modifiedAt:LocalDateTime = LocalDateTime.now()

)