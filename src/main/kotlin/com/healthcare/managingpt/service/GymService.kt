package com.healthcare.managingpt.service

import com.healthcare.managingpt.dto.request.GymCreateRequestDto
import com.healthcare.managingpt.dto.response.*
import com.healthcare.managingpt.model.*
import com.healthcare.managingpt.repository.ApplyClientRepository
import com.healthcare.managingpt.repository.GymCreateRequestRepository
import com.healthcare.managingpt.repository.GymRepository
import com.healthcare.managingpt.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Objects
import java.util.Optional
import javax.servlet.http.HttpServletResponse

@Service
class GymService(
        private var gymRepository: GymRepository,
        private var gymCreateRequestRepository: GymCreateRequestRepository,
        private var userRepository: UserRepository,
        private var applyClientRepository: ApplyClientRepository
) {

    @Transactional
    fun acceptClient(userDetails: UserDetailsImpl, id: Long): AcceptClientResponseDto {
        var res = AcceptClientResponseDto()
        var applyRequest = applyClientRepository.findById(id).get()
        if (userDetails.getUser().userType != User.UserType.OWNER && userDetails.getUser().userType != User.UserType.MANAGER) {
            res.code = HttpServletResponse.SC_BAD_REQUEST
            res.msg = "접근 권한이 없습니다." // 체육관 소유자,또는 매니저가 아님
        } else {
            if (Objects.isNull(applyRequest)) {
                res.code = HttpServletResponse.SC_BAD_REQUEST
                res.msg = "존재하지 않는 요청 ID 입니다."
            } else {
                if (userDetails.getUser().belong!!.id != applyRequest.gym!!.id) {
                    res.code = HttpServletResponse.SC_BAD_REQUEST
                    res.msg = "접근 권한이 없습니다." // 해당 체육관의 소유자, 또는 매니저가 아님
                } else {
                    if (Objects.nonNull(applyRequest.applicant!!.belong)) {
                        res.code = HttpServletResponse.SC_BAD_REQUEST
                        res.msg = "신청 유저가 이미 체육관에 소속된 상태입니다."
                    } else {
                        if (applyRequest.closing) {
                            res.code = HttpServletResponse.SC_BAD_REQUEST
                            res.msg = "이미 종료된 신청건입니다."
                        } else {
                            applyRequest.updateStatus(ApplyClientRequest.Status.ACCEPTED)
                            userDetails.getUser().updateBelong(applyRequest.gym!!)
                            res.code = HttpServletResponse.SC_OK
                            res.msg = "성공적으로 수락 하였습니다."
                            res.simpleApplyRequest = SimpleApplyRequest(applyRequest)
                        }
                    }
                }
            }
        }
        return res
    }

}