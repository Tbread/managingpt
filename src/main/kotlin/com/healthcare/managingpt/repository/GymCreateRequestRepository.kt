package com.healthcare.managingpt.repository

import com.healthcare.managingpt.model.GymCreateRequest
import com.healthcare.managingpt.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface GymCreateRequestRepository:JpaRepository<GymCreateRequest,Long> {
    fun existsByRegistrationNum(registrationNum:String):Boolean
}