package com.healthcare.managingpt.repository

import com.healthcare.managingpt.model.GymCreateRequest
import com.healthcare.managingpt.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface GymCreateRequestRepository:JpaRepository<GymCreateRequest,Long> {
    fun existsByRegistrationNum(registrationNum:String):Boolean
    fun findByApplicant(applicant:User):List<GymCreateRequest>

    fun findByStatus(status:GymCreateRequest.Status):List<GymCreateRequest>
}