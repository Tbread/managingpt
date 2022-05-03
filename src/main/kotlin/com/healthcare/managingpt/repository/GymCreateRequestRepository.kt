package com.healthcare.managingpt.repository

import com.healthcare.managingpt.model.GymCreateRequest
import org.springframework.data.jpa.repository.JpaRepository

interface GymCreateRequestRepository:JpaRepository<GymCreateRequest,Long> {
    fun countByRegistrationNum(registrationNum:String):Int
}