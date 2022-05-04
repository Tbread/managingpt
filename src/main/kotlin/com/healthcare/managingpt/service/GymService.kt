package com.healthcare.managingpt.service

import com.healthcare.managingpt.dto.request.GymCreateRequestDto
import com.healthcare.managingpt.dto.response.GymCreateRequestsViewResponseDto
import com.healthcare.managingpt.dto.response.GymCreateResponseDto
import com.healthcare.managingpt.model.GymCreateRequest
import com.healthcare.managingpt.model.SimpleCreateRequest
import com.healthcare.managingpt.model.User
import com.healthcare.managingpt.repository.GymCreateRequestRepository
import com.healthcare.managingpt.repository.GymRepository
import com.healthcare.managingpt.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.servlet.http.HttpServletResponse

@Service
class GymService(
    private var gymRepository: GymRepository,
    private var gymCreateRequestRepository: GymCreateRequestRepository,
    private var userRepository: UserRepository
) {

    @Transactional
    fun createGym(req:GymCreateRequestDto,userDetails: UserDetailsImpl):GymCreateResponseDto{
        var res = GymCreateResponseDto()
        var regNum:String = req.registrationNum
        if (gymCreateRequestRepository.existsByRegistrationNum(regNum)&& gymRepository.existsByRegistrationNum(regNum)){
            var user:User = userRepository.findByUsername(userDetails.username)!!
            if (user.userType != User.UserType.ADMIN){
                var gymCreateRequest = GymCreateRequest()
                gymCreateRequest.address = req.address
                gymCreateRequest.applicant = user
                gymCreateRequest.tel = req.tel
                gymCreateRequest.name = req.name
                gymCreateRequest.registrationNum = req.registrationNum
                gymCreateRequestRepository.save(gymCreateRequest)
                res.code = HttpServletResponse.SC_OK
                res.msg = "성공적으로 신청하였습니다."
                res.regNum = req.registrationNum
            } else {
                res.code = HttpServletResponse.SC_BAD_REQUEST
                res.msg = "운영진은 할 수 없습니다."
            }

        } else {
            res.code = HttpServletResponse.SC_BAD_REQUEST
            res.msg = "이미 등록상태이거나 등록신청한 사업자등록번호 입니다."
            res.regNum = req.registrationNum
        }
        return res
    }

    fun viewCreateRequests(userDetails: UserDetailsImpl):GymCreateRequestsViewResponseDto{
        var user:User = userDetails.getUser()
        var res: GymCreateRequestsViewResponseDto = GymCreateRequestsViewResponseDto()
        if (user.userType == User.UserType.ADMIN) {
            var requestList: List<GymCreateRequest> = gymCreateRequestRepository.findByApplicant(user)
            var simpleCreateRequests = arrayListOf<SimpleCreateRequest>()
            for (request:GymCreateRequest in requestList){
                var simpleCreateRequest:SimpleCreateRequest = SimpleCreateRequest()
                simpleCreateRequest.requestId=request.id
                simpleCreateRequest.name=request.name
                simpleCreateRequest.tel=request.tel
                simpleCreateRequest.address=request.address
                simpleCreateRequest.registrationNum=request.registrationNum
                simpleCreateRequest.userId= request.applicant!!.id
                simpleCreateRequest.username= request.applicant!!.username
                simpleCreateRequests.add(simpleCreateRequest)
            }
            res.simpleCreateRequests = simpleCreateRequests
            res.code = HttpServletResponse.SC_OK
            res.msg = "성공적으로 불러왔습니다."
        } else {
            res.code = HttpServletResponse.SC_BAD_REQUEST
            res.msg = "권한이 부족합니다."
        }
        return res
    }
}