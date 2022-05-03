package com.healthcare.managingpt.service

import com.healthcare.managingpt.dto.request.UserLoginRequestDto
import com.healthcare.managingpt.dto.request.UserRegisterRequestDto
import com.healthcare.managingpt.dto.response.UserLoginResponseDto
import com.healthcare.managingpt.dto.response.UserRegisterResponseDto
import com.healthcare.managingpt.jwt.JwtTokenProvider
import com.healthcare.managingpt.model.User
import com.healthcare.managingpt.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Objects
import java.util.Optional

@Service
class UserService(
    private var passwordEncoder: BCryptPasswordEncoder,
    private var userRepository: UserRepository,
    private var jwtTokenProvider: JwtTokenProvider
) {

    @Value("\${etc.adminCode}")
    lateinit var adminCode: String

    @Transactional
    fun registerUser(req: UserRegisterRequestDto): UserRegisterResponseDto {
        val username: String = req.username
        val password: String = req.password
        var res: UserRegisterResponseDto = UserRegisterResponseDto()
        var user: User = User()
        if (userRepository.countByUsername(username).equals(0L)) {
            user.username = username
            user.password = passwordEncoder.encode(password)
            user.userType = User.UserType.DEFAULT
            if (req.code == adminCode) {
                res.userType = "ADMIN"
                user.userType = User.UserType.ADMIN
            } else {
                res.userType = "DEFAULT"
            }
            userRepository.save(user)
            res.msg = "성공적으로 생성되었습니다."
            res.success = true
            res.userId = user.id
        } else {
            res.msg = "중복된 아이디입니다."
            res.success = false
        }
        return res
    }

    fun login(req: UserLoginRequestDto): UserLoginResponseDto {
        val username: String = req.username
        val rawPassword: String = req.password
        var res: UserLoginResponseDto = UserLoginResponseDto()
        var user: User? = userRepository.findByUsername(username)
        var password: String? = if (Objects.nonNull(user)) user?.password else null
        if (Objects.nonNull(user) && passwordEncoder.matches(rawPassword, password)) {
            res.msg = "성공적으로 로그인하였습니다."
            res.jwt = jwtTokenProvider.createToken(username, user!!.id)
            res.success = true
            res.username = username

        } else {
            res.msg = "올바르지않은 아이디 또는 패스워드입니다."
            res.success = false
        }
        return res
    }
}