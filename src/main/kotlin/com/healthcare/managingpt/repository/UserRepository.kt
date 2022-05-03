package com.healthcare.managingpt.repository

import com.healthcare.managingpt.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun countByUsername(username: String): Long
    fun countByEmail(email: String): Long
}