package com.example.mateeqkhan.studentattendance

/**
 * Created by M.Ateeq khan on 10/14/2018.
 */
import android.provider.BaseColumns

class Colomns(val key: String, val type: String = "TEXT") {
    companion object {
        val STUDENT_ID = Colomns("userid", "INTEGER PRIMARY KEY AUTOINCREMENT")
        //            val COLUMN_NAME = "name"
//            val COLUMN_AGE = "age"
        val STUDENT_NAME = Colomns("studentname")
        val REGISTER_NUMBER = Colomns("rollnumber")
        val DATE_OF_BIRTH = Colomns("dateOfBirth")
        val FATHER_NAME = Colomns("fathername")
        val CONTACT_NUMBER = Colomns("contactnumber")
        val ADRESS = Colomns("adress")
        val BLOOD_GROUP = Colomns("bloodgroup")
        val SEMESTER = Colomns("semester")
        val SESSION = Colomns("session")
    }
}


class DBTable(val name: String, val columns: Array<Colomns>) {

    companion object {
        val user = DBTable("users", arrayOf(
                Colomns.STUDENT_ID, Colomns.STUDENT_NAME, Colomns.REGISTER_NUMBER,
                Colomns.DATE_OF_BIRTH, Colomns.FATHER_NAME, Colomns.CONTACT_NUMBER,
                Colomns.ADRESS, Colomns.BLOOD_GROUP, Colomns.SEMESTER, Colomns.SESSION
        )
        )
    }

    fun createQuery(): String {
        var query = "CREATE TABLE ${name} ("
        columns.forEach {
            query += "${it.key} ${it.type},"
        }
        query += ","
        return query.replace(",,", ")")
    }

    fun dropQuery(): String = "DROP TABLE IF EXISTS ${name}"

}


object DBContract {


    /* Inner class that defines the table contents */
    class UserEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "users"
            val COLUMN_STUDENT_ID = "userid"
            //            val COLUMN_NAME = "name"
//            val COLUMN_AGE = "age"
            val COLUMN_STUDENT_NAME = "studentname"
            val COLUMN_REGISTER_NUMBER = "rollnumber"
            val COLUMN_DATE_OF_BIRTH = "dateOfBirth"
            val COLUMN_FATHER_NAME = "fathername"
            val COLUMN_CONTACT_NUMBER = "contactnumber"
            val COLUMN_ADRESS = "adress"
            val COLUMN_BLOOD_GROUP = "bloodgroup"
            val COLUMN_SEMESTER = "semester"
            val COLUMN_SESSION = "session"


            // val COLUMN_DATE_OF_BIRTH = "date"
        }
    }


    class UserAttendence : BaseColumns {
        companion object {
            val TABLE_NAME_ATTENDENCE = "attendence"
            val COLUMN_STUDENT_ID = "userid"
            val COLUMN_STUDENT_NAME = "studentname"
            val COLUMN_REGISTER_NUMBER = "rollnumber"
            val COLUMN_DATE_OF_BIRTH = "dateOfBirth"
            val COLUMN_FATHER_NAME = "fathername"
            val COLUMN_CONTACT_NUMBER = "contactnumber"
            val COLUMN_ADRESS = "adress"
            val COLUMN_BLOOD_GROUP = "bloodgroup"
            val COLUMN_SEMESTER = "semester"
            val COLUMN_SESSION = "session"
            val COLUMN_ATTENDENCE_DATE = "attendenceDate"
            val COLUMN_STUDENT_ATTENDENCE = "studentname"
            val COLUMN_STUDENT_TOTAL_STRENGTH = "strength"
            val COLUMN_STUDENT_PRESENT = "present"
            val COLUMN_STUDENT_TOTAL_PRESENT = "totalpresent"
            val COLUMN_STUDENT_TOTAL_ABSENT = "totalabsent"
            val COLUMN_STUDENT_LEAVE = "leave"
            val COLUMN_STUDENT_ABSENT = "absent"
            // val COLUMN_DATE_OF_BIRTH = "date"
        }
    }
}