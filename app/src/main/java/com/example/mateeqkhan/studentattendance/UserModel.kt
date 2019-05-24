package com.example.mateeqkhan.studentattendance

import android.content.ContentValues
import android.database.Cursor

/**
 * Created by M.Ateeq khan on 10/14/2018.
 */
//class UserModel(val userid: String, val name: String, val age: String)
class UserModel {

    var studentId: Int = 0
    var studentname: String = ""
    var rollnumber: String = ""
    var dateOfBirth: String = ""
    var fathername: String = ""
    var contactnumber: String = ""
    var adress: String = ""
    var bloodgroup: String = ""
    var semester: String = ""
    var session: String = ""

    constructor()

    constructor(cursor: Cursor) {
        studentId = cursor.getInt(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_STUDENT_ID))
        studentname = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_STUDENT_NAME))
        rollnumber = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_REGISTER_NUMBER))
        dateOfBirth = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUNN_DATE_OF_BIRTH))
        fathername = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_FATHER_NAME))
        contactnumber = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_CONTACT_NUMBER))
        adress = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_ADRESS))
        bloodgroup = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_BLOOD_GROUP))
        semester = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUNN_SEMESTER))
        session = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_SESSION))
    }

    companion object {
        fun userValues(user: UserModel): ContentValues {
            var values = ContentValues()
            values.put(DBContract.UserEntry.COLUMN_STUDENT_ID, user.studentId)
            values.put(DBContract.UserEntry.COLUMN_STUDENT_NAME, user.studentname)
            values.put(DBContract.UserEntry.COLUMN_REGISTER_NUMBER, user.rollnumber)
            values.put(DBContract.UserEntry.COLUMN_REGISTER_NUMBER, user.rollnumber)
            values.put(DBContract.UserEntry.COLUNN_DATE_OF_BIRTH, user.dateOfBirth)
            values.put(DBContract.UserEntry.COLUMN_FATHER_NAME, user.fathername)
            values.put(DBContract.UserEntry.COLUMN_CONTACT_NUMBER, user.contactnumber)
            values.put(DBContract.UserEntry.COLUMN_ADRESS, user.adress)
            values.put(DBContract.UserEntry.COLUMN_BLOOD_GROUP, user.bloodgroup)
            values.put(DBContract.UserEntry.COLUNN_SEMESTER, user.semester)
            values.put(DBContract.UserEntry.COLUMN_SESSION, user.session)
            return values
        }
    }
}

class UserAttendenceModel {
    var studentId: Int = 0
    var studentname: String = ""
    var rollnumber: String = ""
    var dateOfBirth: String = ""
    var fathername: String = ""
    var contactnumber: String = ""
    var adress: String = ""
    var bloodgroup: String = ""
    var semester: String = ""
    var session: String = ""
    var attendencedate: String = ""
    var present: String = ""
    var absent: String = ""
    var leave: String = ""

    constructor()

    constructor(cursor: Cursor) {
        studentId = cursor.getInt(cursor.getColumnIndex(DBContract.UserAttendence.COLUMN_STUDENT_ID))
        studentname = cursor.getString(cursor.getColumnIndex(DBContract.UserAttendence.COLUMN_STUDENT_NAME))
        rollnumber = cursor.getString(cursor.getColumnIndex(DBContract.UserAttendence.COLUMN_REGISTER_NUMBER))
        dateOfBirth = cursor.getString(cursor.getColumnIndex(DBContract.UserAttendence.COLUNN_DATE_OF_BIRTH))
        fathername = cursor.getString(cursor.getColumnIndex(DBContract.UserAttendence.COLUMN_FATHER_NAME))
        contactnumber = cursor.getString(cursor.getColumnIndex(DBContract.UserAttendence.COLUMN_CONTACT_NUMBER))
        adress = cursor.getString(cursor.getColumnIndex(DBContract.UserAttendence.COLUMN_ADRESS))
        bloodgroup = cursor.getString(cursor.getColumnIndex(DBContract.UserAttendence.COLUMN_BLOOD_GROUP))
        semester = cursor.getString(cursor.getColumnIndex(DBContract.UserAttendence.COLUNN_SEMESTER))
        session = cursor.getString(cursor.getColumnIndex(DBContract.UserAttendence.COLUMN_SESSION))
        attendencedate = cursor.getString(cursor.getColumnIndex(DBContract.UserAttendence.COLUMN_ATTENDENCE_DATE))
        present = cursor.getString(cursor.getColumnIndex(DBContract.UserAttendence.COLUMN_STUDENT_PRESENT))
        absent = cursor.getString(cursor.getColumnIndex(DBContract.UserAttendence.COLUMN_STUDENT_ABSENT))
        leave = cursor.getString(cursor.getColumnIndex(DBContract.UserAttendence.COLUMN_STUDENT_LEAVE))

    }
}
