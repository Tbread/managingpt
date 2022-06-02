package com.healthcare.managingpt.service

import com.healthcare.managingpt.dto.request.GymManagerRequestDto
import com.healthcare.managingpt.dto.response.GymManagerResponseDto
import com.healthcare.managingpt.model.GymManager
import com.healthcare.managingpt.model.User
import com.healthcare.managingpt.repository.GymManagerRepository
import com.healthcare.managingpt.repository.GymRepository
import com.healthcare.managingpt.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.servlet.http.HttpServletResponse


@Service
class GymManagerService(
    private val gymManagerRepository: GymManagerRepository,
    private val userRepository: UserRepository,
    private val gymRepository: GymRepository
) {

    @Transactional
    fun appointManager(userDetails: UserDetailsImpl, req: GymManagerRequestDto): GymManagerResponseDto {
        var res = GymManagerResponseDto()
        if (userDetails.getUser().userType != User.UserType.OWNER) {
            res.code = HttpServletResponse.SC_BAD_REQUEST
            res.msg = "체육관 소유자만 접근 가능한 기능입니다."
        } else {
            val user = userRepository.findByUsername(req.username)
            if (Objects.isNull(user)) {
                res.code = HttpServletResponse.SC_BAD_REQUEST
                res.msg = "존재하지 않는 유저 아이디 입니다."
            } else {
                if (user!!.userType != User.UserType.DEFAULT) {
                    res.code = HttpServletResponse.SC_BAD_REQUEST
                    res.msg = "체육관 소유자 또는 매니저가 아닌 경우에만 매니저로 등록 가능합니다."
                } else {
                    val gymManager = GymManager()
                    val gym = gymRepository.findByOwner(userDetails.getUser())
                    gymManager.managerId = user
                    gymManager.gymId = gym
                    gymManagerRepository.save(gymManager)
                    user.updateUserType(User.UserType.MANAGER)
                    user.updateBelong(gym!!)
                    res.code = HttpServletResponse.SC_OK
                    res.msg = "성공적으로 매니저로 임명하였습니다."
                    res.username = req.username
                    res.gymName = gym!!.name
                }
            }
        }
        return res
    }
}