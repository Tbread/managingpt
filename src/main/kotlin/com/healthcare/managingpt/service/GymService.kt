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
import java.util.Objects
import java.util.Optional
import javax.servlet.http.HttpServletResponse

@Service
class GymService(
    private var gymRepository: GymRepository,
    private var gymCreateRequestRepository: GymCreateRequestRepository,
    private var userRepository: UserRepository
) {

}