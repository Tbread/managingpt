package com.healthcare.managingpt.model


import lombok.NoArgsConstructor
import javax.persistence.*

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

    @ManyToOne
    @JoinColumn
    var belong:Gym? = null

    fun passwordReset(password: String) {
        this.password = password
    }

    fun updateUserType(userType:UserType){
        this.userType = userType
    }

    fun updateBelong(gym:Gym){
        this.belong = gym
    }

}