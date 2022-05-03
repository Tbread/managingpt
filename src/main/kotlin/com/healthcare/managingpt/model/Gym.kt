package com.healthcare.managingpt.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne


@Entity
class Gym :TimeStamped(){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long = 0

    @Column(nullable = false)
    var name:String = ""

    @Column(nullable = false)
    var tel:String = ""

    @Column(nullable = false)
    var address:String = ""

    @Column(nullable = false)
    var registrationNum:String = ""

    @ManyToOne
    @JoinColumn
    var owner:User? = null
}