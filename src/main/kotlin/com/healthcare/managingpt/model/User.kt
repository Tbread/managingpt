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
        ADMIN,
        MANAGER,
        DEFAULT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(nullable = false)
    var username: String = ""

    @Column(nullable = false)
    var password: String = ""

    @Column(nullable = false)
    var userType: UserType = UserType.DEFAULT

}