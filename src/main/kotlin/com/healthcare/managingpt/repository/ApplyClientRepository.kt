package com.healthcare.managingpt.repository

import com.healthcare.managingpt.model.ApplyClientRequest
import com.healthcare.managingpt.model.Gym
import com.healthcare.managingpt.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface ApplyClientRepository:JpaRepository<ApplyClientRequest,Long> {

    fun findByGym(gym:Gym):List<ApplyClientRequest>

    fun existsByGymAndApplicantAndClosing(gym: Gym,applicant:User,closing:Boolean):Boolean

    fun findByGymAndClosing(gym:Gym,closing: Boolean):List<ApplyClientRequest>
}