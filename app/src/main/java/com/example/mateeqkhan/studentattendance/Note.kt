package com.example.mateeqkhan.studentattendance

/**
 * Created by M.Ateeq khan on 9/26/2018.
 */
class Note {

    var id: Int? = null
    var name: String? = null
    var regno: String? = null
    var studentClass:String? = null
    constructor(id: Int, name: String, regNO: String,studentClass:String) {
        this.id = id
        this.name = name
        this.regno = regno
        this.studentClass = studentClass
        //this.content = content

    }

}