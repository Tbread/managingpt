package com.healthcare.managingpt.controller

import com.healthcare.managingpt.dto.request.GymManagerRequestDto
import com.healthcare.managingpt.dto.response.GymManagerResponseDto
import com.healthcare.managingpt.service.GymManagerService
import com.healthcare.managingpt.service.UserDetailsImpl
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/gym-manager")
class GymManagerController(private val gymManagerService: GymManagerService) {

    @PostMapping("/appoint-manager")
    fun appointManager(userDetails: UserDetailsImpl,req:GymManagerRequestDto): GymManagerResponseDto {
        return gymManagerService.appointManager(userDetails,req)
    }
}