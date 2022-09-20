package com.healthcare.managingpt.dto.response

import com.healthcare.managingpt.model.SimpleApplyRequest

class AcceptClientResponseDto {
    var code:Int = 0
    var msg:String? = ""
    var simpleApplyRequest:SimpleApplyRequest? = null
}