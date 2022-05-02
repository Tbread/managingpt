package com.healthcare.managingpt.controller

import com.healthcare.managingpt.dto.request.UserLoginRequestDto
import com.healthcare.managingpt.dto.request.UserRegisterRequestDto
import com.healthcare.managingpt.dto.response.UserLoginResponseDto
import com.healthcare.managingpt.dto.response.UserRegisterResponseDto
import com.healthcare.managingpt.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(private var userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody req:UserRegisterRequestDto):UserRegisterResponseDto{
        return userService.registerUser(req)
    }

    @PostMapping("/login")
    fun login(@RequestBody req:UserLoginRequestDto):UserLoginResponseDto{
        return userService.login(req)
    }
}