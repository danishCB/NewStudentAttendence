package com.example.mateeqkhan.studentattendance

/**
 * Created by M.Ateeq khan on 10/14/2018.
 */
import android.provider.BaseColumns

object DBContract {

    /* Inner class that defines the table contents */
    class UserEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "users"
//            val COLUMN_USER_ID = "userid"
//            val COLUMN_NAME = "name"
//            val COLUMN_AGE = "age"
            val COLUMN_STUDENT_NAME = "studentname"
            val COLUMN_REGISTER_NUMBER = "rollnumber"
            val COLUNN_DATE_OF_BIRTH = "dateOfBirth"
            val COLUMN_FATHER_NAME = "fathername"
            val COLUMN_CONTACT_NUMBER = "contactnumber"
            val COLUMN_ADRESS = "adress"
            val COLUMN_BLOOD_GROUP = "bloodgroup"
            val COLUNN_SEMESTER = "semester"
            val COLUMN_SESSION = "session"


            // val COLUMN_DATE_OF_BIRTH = "date"
        }
    }
}