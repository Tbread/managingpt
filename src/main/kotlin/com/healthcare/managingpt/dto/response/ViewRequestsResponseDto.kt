package com.healthcare.managingpt.dto.response

import com.healthcare.managingpt.model.SimpleApplyRequest

class ViewRequestsResponseDto {
    var code = 0
    var msg:String? = ""
    var requests:List<SimpleApplyRequest>? = null
}