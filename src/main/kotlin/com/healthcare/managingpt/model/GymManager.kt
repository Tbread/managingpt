package com.healthcare.managingpt.model

import javax.persistence.*

@Entity
class GymManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long = 0

    @OneToOne
    @JoinColumn
    var managerId:User? = null

    @ManyToOne
    @JoinColumn
    var gymId:Gym? = null


}