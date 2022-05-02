package com.healthcare.managingpt.service

import com.healthcare.managingpt.model.User
import com.healthcare.managingpt.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class UserDetailsServiceImpl(private var userRepository: UserRepository) : UserDetailsService {


    override fun loadUserByUsername(username: String): UserDetails {
        var user: User = userRepository.findByUsername(username)!!
        return UserDetailsImpl(user)
    }
}