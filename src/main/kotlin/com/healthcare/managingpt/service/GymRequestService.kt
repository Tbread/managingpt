package com.healthcare.managingpt.service

import com.healthcare.managingpt.dto.request.GymCreateRequestDto
import com.healthcare.managingpt.dto.response.GymCreateDenyResponseDto
import com.healthcare.managingpt.dto.response.GymCreatePermitResponseDto
import com.healthcare.managingpt.dto.response.GymCreateRequestsViewResponseDto
import com.healthcare.managingpt.dto.response.GymCreateResponseDto
import com.healthcare.managingpt.model.Gym
import com.healthcare.managingpt.model.GymCreateRequest
import com.healthcare.managingpt.model.SimpleCreateRequest
import com.healthcare.managingpt.model.User
import com.healthcare.managingpt.repository.GymCreateRequestRepository
import com.healthcare.managingpt.repository.GymRepository
import com.healthcare.managingpt.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.servlet.http.HttpServletResponse

@Service
class GymRequestService(
    private var gymRepository: GymRepository,
    private var gymCreateRequestRepository: GymCreateRequestRepository,
    private var userRepository: UserRepository
) {

    @Transactional
    fun createGym(req: GymCreateRequestDto, userDetails: UserDetailsImpl): GymCreateResponseDto {
        var res = GymCreateResponseDto()
        var regNum: String = req.registrationNum
        if (gymCreateRequestRepository.existsByRegistrationNum(regNum) && gymRepository.existsByRegistrationNum(regNum)) {
            var user: User = userRepository.findByUsername(userDetails.username)!!
            if (user.userType != User.UserType.ADMIN) {
                var gymCreateRequest = GymCreateRequest()
                gymCreateRequest.address = req.address
                gymCreateRequest.applicant = user
                gymCreateRequest.tel = req.tel
                gymCreateRequest.name = req.name
                gymCreateRequest.registrationNum = req.registrationNum
                gymCreateRequestRepository.save(gymCreateRequest)
                res.code = HttpServletResponse.SC_OK
                res.msg = "??????????????? ?????????????????????."
                res.regNum = req.registrationNum
            } else {
                res.code = HttpServletResponse.SC_BAD_REQUEST
                res.msg = "???????????? ??? ??? ????????????."
            }

        } else {
            res.code = HttpServletResponse.SC_BAD_REQUEST
            res.msg = "?????? ????????????????????? ??????????????? ????????????????????? ?????????."
            res.regNum = req.registrationNum
        }
        return res
    }

    fun viewCreateRequests(userDetails: UserDetailsImpl): GymCreateRequestsViewResponseDto {
        var user: User = userDetails.getUser()
        var res: GymCreateRequestsViewResponseDto = GymCreateRequestsViewResponseDto()
        if (user.userType == User.UserType.ADMIN) {
            var requestList: List<GymCreateRequest> =
                gymCreateRequestRepository.findByStatus(GymCreateRequest.Status.AWAIT)
            var simpleCreateRequests = arrayListOf<SimpleCreateRequest>()
            for (request: GymCreateRequest in requestList) {
                var simpleCreateRequest: SimpleCreateRequest = SimpleCreateRequest(request)
                simpleCreateRequests.add(simpleCreateRequest)
            }
            res.simpleCreateRequests = simpleCreateRequests
            res.code = HttpServletResponse.SC_OK
            res.msg = "??????????????? ??????????????????."
        } else {
            res.code = HttpServletResponse.SC_BAD_REQUEST
            res.msg = "????????? ???????????????."
        }
        return res
    }

    @Transactional
    fun permitRequest(id: Long, userDetails: UserDetailsImpl): GymCreatePermitResponseDto {
        var res: GymCreatePermitResponseDto = GymCreatePermitResponseDto()
        if (userDetails.getUser().userType == User.UserType.ADMIN) {
            val gymCreateRequest: Optional<GymCreateRequest> = gymCreateRequestRepository.findById(id)
            if (Objects.nonNull(gymCreateRequest)) {
                if (gymCreateRequest.get().status == GymCreateRequest.Status.AWAIT) {
                    var gym: Gym = Gym()
                    gym.address = gymCreateRequest.get().address
                    gym.tel = gymCreateRequest.get().tel
                    gym.name = gymCreateRequest.get().name
                    gym.registrationNum = gymCreateRequest.get().registrationNum
                    gym.owner = gymCreateRequest.get().applicant
                    gymRepository.save(gym)
                    gymCreateRequest.get().updateStatus(GymCreateRequest.Status.ACCEPTED)
                    gymCreateRequest.get().applicant!!.updateUserType(User.UserType.OWNER)
                    res.code = HttpServletResponse.SC_OK
                    res.msg = "??????????????? ?????????????????????."
                    res.request = SimpleCreateRequest(gymCreateRequest!!.get())
                } else {
                    res.code = HttpServletResponse.SC_BAD_REQUEST
                    res.msg = "?????? ?????? ?????? ????????? ???????????????."
                }
            } else {
                res.code = HttpServletResponse.SC_BAD_REQUEST
                res.msg = "?????????????????? ??????ID?????????"
            }
        } else {
            res.code = HttpServletResponse.SC_BAD_REQUEST
            res.msg = "????????? ???????????????."
        }
        return res
    }

    @Transactional
    fun denyRequest(id: Long, userDetails: UserDetailsImpl): GymCreateDenyResponseDto {
        var res = GymCreateDenyResponseDto()
        if (userDetails.getUser().userType == User.UserType.ADMIN) {
            val request = gymCreateRequestRepository.findById(id)
            if (Objects.nonNull(request)) {
                if (request.get().status == GymCreateRequest.Status.AWAIT) {
                    request.get().updateStatus(GymCreateRequest.Status.DENIED)
                    res.code = HttpServletResponse.SC_OK
                    res.msg = "??????????????? ?????????????????????."
                    res.request = SimpleCreateRequest(request.get())
                } else {
                    res.code = HttpServletResponse.SC_BAD_REQUEST
                    res.msg = "?????? ?????? ?????? ????????? ???????????????."
                }
            } else {
                res.code = HttpServletResponse.SC_BAD_REQUEST
                res.msg = "?????????????????? ?????? ID?????????."
            }
        } else {
            res.code = HttpServletResponse.SC_BAD_REQUEST
            res.msg = "????????? ???????????????."
        }
        return res
    }
}