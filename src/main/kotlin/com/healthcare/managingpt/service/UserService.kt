package com.healthcare.managingpt.service

import com.healthcare.managingpt.dto.request.UserLoginRequestDto
import com.healthcare.managingpt.dto.request.UserPasswordResetRequestDto
import com.healthcare.managingpt.dto.request.UserRegisterRequestDto
import com.healthcare.managingpt.dto.response.UserLoginResponseDto
import com.healthcare.managingpt.dto.response.UserPasswordResetResponseDto
import com.healthcare.managingpt.dto.response.UserRegisterResponseDto
import com.healthcare.managingpt.jwt.JwtTokenProvider
import com.healthcare.managingpt.model.User
import com.healthcare.managingpt.repository.UserRepository
import com.healthcare.managingpt.tools.RandomizedString
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Objects
import javax.servlet.http.HttpServletResponse

@Service
class UserService(
    private var passwordEncoder: BCryptPasswordEncoder,
    private var userRepository: UserRepository,
    private var jwtTokenProvider: JwtTokenProvider,
    private var javaMailSender: JavaMailSender
) {

    @Value("\${etc.adminCode}")
    lateinit var adminCode: String

    @Transactional
    fun registerUser(req: UserRegisterRequestDto): UserRegisterResponseDto {
        val username: String = req.username
        val password: String = req.password
        var res: UserRegisterResponseDto = UserRegisterResponseDto()
        var user: User = User()
        if (userRepository.existsByUsername(username)&& userRepository.existsByEmail(req.email)) {
            user.username = username
            user.password = passwordEncoder.encode(password)
            user.userType = User.UserType.DEFAULT
            user.email = req.email
            if (req.code == adminCode) {
                res.userType = "ADMIN"
                user.userType = User.UserType.ADMIN
            } else {
                res.userType = "DEFAULT"
            }
            userRepository.save(user)
            res.msg = "??????????????? ?????????????????????."
            res.success = true
            res.userId = user.id
        } else {
            res.msg = "????????? ????????? ?????? ??????????????????."
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
            res.msg = "??????????????? ????????????????????????."
            res.jwt = jwtTokenProvider.createToken(username, user!!.id)
            res.success = true
            res.username = username

        } else {
            res.msg = "?????????????????? ????????? ?????? ?????????????????????."
            res.success = false
        }
        return res
    }

    @Transactional
    fun passwordResetRequest(req: UserPasswordResetRequestDto):UserPasswordResetResponseDto{
        var res:UserPasswordResetResponseDto = UserPasswordResetResponseDto()
        var user: User? = userRepository.findByUsernameAndEmail(req.username,req.email)
        if (Objects.nonNull(user)){
            var randomizeKey:String = RandomizedString().randomAlphabetNumber(12)
            var email:SimpleMailMessage = SimpleMailMessage()
            email.setSubject("?????? ???????????? ??????")
            email.setText("??????????????????:"+randomizeKey)
            email.setTo(user!!.email)
            javaMailSender.send(email)
            user.passwordReset(passwordEncoder.encode(randomizeKey))
            res.msg = "??????????????? ????????? ?????????????????????."
            res.code = HttpServletResponse.SC_OK
        } else {
            res.msg = "???????????? ?????? ?????? ???????????????."
            res.code = HttpServletResponse.SC_BAD_REQUEST
        }
        return res
    }
}