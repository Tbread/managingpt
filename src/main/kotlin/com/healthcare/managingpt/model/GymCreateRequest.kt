package com.healthcare.managingpt.model

import javax.persistence.*

@Entity
class GymCreateRequest:TimeStamped(){

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
    var applicant:User? = null
}