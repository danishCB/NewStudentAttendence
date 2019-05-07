package com.example.mateeqkhan.studentattendance

/**
 * Created by M.Ateeq khan on 9/20/2018.
 */

import java.io.Serializable

class UserAccount : Serializable {

    var userName: String? = null
    var userType: String? = null

    var isActive: Boolean = false

    constructor(userName: String, userType: String) {
        this.userName = userName
        this.userType = userType
        this.isActive = true
    }

    constructor(userName: String, userType: String, active: Boolean) {
        this.userName = userName
        this.userType = userType
        this.isActive = active
    }

    override fun toString(): String {
        return this.userName + " (" + this.userType + ")"
    }

}

