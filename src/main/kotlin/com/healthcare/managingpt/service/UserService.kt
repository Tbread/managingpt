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
import net.bytebuddy.utility.RandomString
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.SecureRandom
import java.util.Objects
import javax.mail.internet.MimeMessage
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
        if (userRepository.countByUsername(username) == 0L && userRepository.countByEmail(req.email) == 0L) {
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
            res.msg = "성공적으로 생성되었습니다."
            res.success = true
            res.userId = user.id
        } else {
            res.msg = "중복된 아이디 또는 이메일입니다."
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

    @Transactional
    fun passwordResetRequest(req: UserPasswordResetRequestDto):UserPasswordResetResponseDto{
        var res:UserPasswordResetResponseDto = UserPasswordResetResponseDto()
        var user: User? = userRepository.findByUsernameAndEmail(req.username,req.email)
        if (Objects.nonNull(user)){
            var randomizeKey:String = Math.random().toString().substring(3)
            var email:SimpleMailMessage = SimpleMailMessage()
            email.setSubject("임시 비밀번호 발송")
            email.setText("임시비밀번호:"+randomizeKey)
            email.setTo(user!!.email)
            javaMailSender.send(email)
            user.passwordReset(passwordEncoder.encode(randomizeKey))
            res.msg = "성공적으로 메일을 발송하였습니다."
            res.code = HttpServletResponse.SC_OK
        } else {
            res.msg = "존재하지 않는 계정 정보입니다."
            res.code = HttpServletResponse.SC_BAD_REQUEST
        }
        return res
    }
}