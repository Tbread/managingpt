package com.healthcare.managingpt.model


import lombok.NoArgsConstructor
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@NoArgsConstructor
class User : TimeStamped() {
    enum class UserType {
        DEFAULT,
        MANAGER,
        OWNER,
        ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(nullable = false)
    var email: String = ""

    @Column(nullable = false)
    var username: String = ""

    @Column(nullable = false)
    var password: String = ""

    @Column(nullable = false)
    var userType: UserType = UserType.DEFAULT

    fun passwordReset(password: String) {
        this.password = password
    }

    fun updateUserType(userType:UserType){
        this.userType = userType
    }

}