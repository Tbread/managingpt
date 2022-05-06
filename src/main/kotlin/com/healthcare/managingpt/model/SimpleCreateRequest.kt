package com.healthcare.managingpt.model

class SimpleCreateRequest {
    var requestId:Long = 0
    var name:String = ""
    var tel:String = ""
    var address:String = ""
    var registrationNum:String = ""
    var userId:Long = 0
    var username:String = ""


    constructor(req:GymCreateRequest){
        this.requestId = req.id
        this.tel =req.tel
        this.address = req.address
        this.name = req.name
        this.registrationNum = req.registrationNum
        this.username = req.applicant!!.username
    }
}