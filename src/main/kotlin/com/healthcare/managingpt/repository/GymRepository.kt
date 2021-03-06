package com.healthcare.managingpt.repository

import com.healthcare.managingpt.model.Gym
import com.healthcare.managingpt.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface GymRepository:JpaRepository<Gym,Long> {
    fun existsByRegistrationNum(registrationNum:String):Boolean
    fun findByOwner(user:User):Gym?
}