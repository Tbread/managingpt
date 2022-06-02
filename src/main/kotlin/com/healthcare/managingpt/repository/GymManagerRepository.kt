package com.healthcare.managingpt.repository

import com.healthcare.managingpt.model.GymManager
import org.springframework.data.jpa.repository.JpaRepository

interface GymManagerRepository : JpaRepository<GymManager,Long> {

    fun findByManagerId(managerId:Long):GymManager?
}