package com.healthcare.managingpt.controller

import com.healthcare.managingpt.dto.request.ApplyClientRequestDto
import com.healthcare.managingpt.dto.request.UserLoginRequestDto
import com.healthcare.managingpt.dto.request.UserPasswordResetRequestDto
import com.healthcare.managingpt.dto.request.UserRegisterRequestDto
import com.healthcare.managingpt.dto.response.ApplyClientResponseDto
import com.healthcare.managingpt.dto.response.UserLoginResponseDto
import com.healthcare.managingpt.dto.response.UserPasswordResetResponseDto
import com.healthcare.managingpt.dto.response.UserRegisterResponseDto
import com.healthcare.managingpt.service.UserDetailsImpl
import com.healthcare.managingpt.service.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(private var userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody req: UserRegisterRequestDto): UserRegisterResponseDto {
        return userService.registerUser(req)
    }

    @PostMapping("/login")
    fun login(@RequestBody req: UserLoginRequestDto): UserLoginResponseDto {
        return userService.login(req)
    }

    @PostMapping("/pass-reset")
    fun passwordReset(@RequestBody req: UserPasswordResetRequestDto): UserPasswordResetResponseDto {
        return userService.passwordResetRequest(req)
    }

    @PostMapping("/apply-client")
    fun applyClient(@AuthenticationPrincipal userDetails: UserDetailsImpl, @RequestBody req: ApplyClientRequestDto):ApplyClientResponseDto {
        return userService.applyClient(userDetails,req)
    }
}