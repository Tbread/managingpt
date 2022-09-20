package com.healthcare.managingpt.model

class SimpleApplyRequest {
    var requestId: Long = 0
    var username: String? = ""
    var userId: Long = 0
    var gymId: Long = 0
    var gymName: String? = ""


    constructor(req: ApplyClientRequest) {
        this.requestId = req.id
        this.username = req.applicant!!.username
        this.gymId = req.gym!!.id
        this.userId = req.applicant!!.id
        this.gymName = req.gym!!.name
    }
}