package com.healthcare.managingpt.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class ApplyClientRequest:TimeStamped() {
    enum class Status{
        AWAIT,
        ACCEPTED,
        DENIED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long = 0

    @ManyToOne
    @JoinColumn
    var applicant:User? = null

    @ManyToOne
    @JoinColumn
    var gym:Gym? = null

    @Column(nullable = false)
    var status = ApplyClientRequest.Status.AWAIT

    @Column(nullable = false)
    var closing = false

    fun updateStatus(status:Status){
        this.status = status
        this.closing = true
    }

}