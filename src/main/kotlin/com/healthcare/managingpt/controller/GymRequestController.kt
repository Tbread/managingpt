package com.healthcare.managingpt.controller

import com.healthcare.managingpt.dto.request.GymCreateRequestDto
import com.healthcare.managingpt.dto.response.GymCreateDenyResponseDto
import com.healthcare.managingpt.dto.response.GymCreatePermitResponseDto
import com.healthcare.managingpt.dto.response.GymCreateRequestsViewResponseDto
import com.healthcare.managingpt.dto.response.GymCreateResponseDto
import com.healthcare.managingpt.service.GymRequestService
import com.healthcare.managingpt.service.UserDetailsImpl
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/gym-request")
class GymRequestController(private var gymRequestService: GymRequestService) {

    @PostMapping("/create")
    fun createGym(
        @RequestBody req: GymCreateRequestDto,
        @AuthenticationPrincipal userDetails: UserDetailsImpl
    ): GymCreateResponseDto {
        return gymRequestService.createGym(req, userDetails)
    }

    @GetMapping("/view-request")
    fun viewCreateRequests(@AuthenticationPrincipal userDetails: UserDetailsImpl): GymCreateRequestsViewResponseDto {
        return gymRequestService.viewCreateRequests(userDetails)
    }

    @GetMapping("/permit-request/{id}")
    fun permitRequest(
        @AuthenticationPrincipal userDetails: UserDetailsImpl,
        @PathVariable id: Long
    ): GymCreatePermitResponseDto {
        return gymRequestService.permitRequest(id, userDetails)
    }

    @GetMapping("/deny-request/{id}")
    fun denyRequest(
        @AuthenticationPrincipal userDetails: UserDetailsImpl,
        @PathVariable id: Long
    ): GymCreateDenyResponseDto {
        return gymRequestService.denyRequest(id, userDetails)
    }
}