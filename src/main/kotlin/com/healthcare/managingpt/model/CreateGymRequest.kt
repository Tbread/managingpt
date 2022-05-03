package com.healthcare.managingpt.model

import javax.persistence.*

@Entity
class CreateGymRequest:TimeStamped(){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long = 0

    @Column(nullable = false)
    var name:String = ""

    @Column(nullable = false)
    var tel:String = ""

    @Column(nullable = false)
    var address:String = ""

    @ManyToOne
    @JoinColumn
    var applicant:User? = null
}