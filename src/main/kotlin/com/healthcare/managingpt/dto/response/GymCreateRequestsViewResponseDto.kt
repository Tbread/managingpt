package com.healthcare.managingpt.dto.response

import com.healthcare.managingpt.model.SimpleCreateRequest

class GymCreateRequestsViewResponseDto {

    var code:Int = 0
    var msg:String? = null
    var simpleCreateRequests:ArrayList<SimpleCreateRequest>? = null
}