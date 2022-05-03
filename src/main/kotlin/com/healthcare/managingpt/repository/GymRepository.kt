package com.healthcare.managingpt.repository

import com.healthcare.managingpt.model.Gym
import org.springframework.data.jpa.repository.JpaRepository

interface GymRepository:JpaRepository<Gym,Long> {
    fun countByRegistrationNum(registrationNum:String):Int
}