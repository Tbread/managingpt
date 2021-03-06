package com.healthcare.managingpt.controller

import com.healthcare.managingpt.dto.request.GymCreateRequestDto
import com.healthcare.managingpt.dto.response.GymCreateDenyResponseDto
import com.healthcare.managingpt.dto.response.GymCreatePermitResponseDto
import com.healthcare.managingpt.dto.response.GymCreateRequestsViewResponseDto
import com.healthcare.managingpt.dto.response.GymCreateResponseDto
import com.healthcare.managingpt.service.GymService
import com.healthcare.managingpt.service.UserDetailsImpl
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/gym")
class GymController(private var gymService: GymService) {

}