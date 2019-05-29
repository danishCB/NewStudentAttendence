package com.example.mateeqkhan.studentattendance

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.view.autofill.AutofillId
import java.util.ArrayList
import java.nio.file.Files.size
import android.database.DatabaseUtils
import java.nio.file.Files.delete


/**import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList
 * Created by M.Ateeq khan on 10/21/2018.
 */
class UserDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DBTable.user.createQuery())
        db.execSQL(SQL_CREATE_ATEENDENCE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(DBTable.user.dropQuery())
        db.execSQL(SQL_DELETE_ATTENDENCE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertUser(user: UserModel): Boolean {
        // Gets the data repository in write mode
        val db = this.writableDatabase

        // Create a new map of values, where column names are the keys
        val values = UserModel.userValues(user)
        /*  values.put(DBContract.UserEntry.COLUMN_STUDENT_ID, user.studentId)
          values.put(DBContract.UserEntry.COLUMN_STUDENT_NAME, user.studentname)
          values.put(DBContract.UserEntry.COLUMN_REGISTER_NUMBER, user.rollnumber)
          values.put(DBContract.UserEntry.COLUMN_REGISTER_NUMBER, user.rollnumber)
          values.put(DBContract.UserEntry.COLUNN_DATE_OF_BIRTH, user.dateOfBirth)
          values.put(DBContract.UserEntry.COLUMN_FATHER_NAME, user.fathername)
          values.put(DBContract.UserEntry.COLUMN_CONTACT_NUMBER, user.contactnumber)
          values.put(DBContract.UserEntry.COLUMN_ADRESS, user.adress)
          values.put(DBContract.UserEntry.COLUMN_BLOOD_GROUP, user.bloodgroup)
          values.put(DBContract.UserEntry.COLUNN_SEMESTER, user.semester)
          values.put(DBContract.UserEntry.COLUMN_SESSION, user.session)*/

        // values.put(DBContract.UserEntry.COLUMN_AGE, user.age)

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContract.UserEntry.TABLE_NAME, null, values)
        db.close()

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun insertUserAttendence(userAttendenceList: UserAttendenceModel/*userAttendence: UserAttendenceModel*/, update: Boolean): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys

        val values = ContentValues()
        //values.put(DBContract.UserAttendence.COLUMN_STUDENT_ID, userAttendenceList.studentId)
        values.put(DBContract.UserAttendence.COLUMN_STUDENT_NAME, userAttendenceList.studentname)
        values.put(DBContract.UserAttendence.COLUMN_REGISTER_NUMBER, userAttendenceList.rollnumber)
        values.put(DBContract.UserAttendence.COLUMN_REGISTER_NUMBER, userAttendenceList.rollnumber)
        values.put(DBContract.UserAttendence.COLUMN_DATE_OF_BIRTH, userAttendenceList.dateOfBirth)
        values.put(DBContract.UserAttendence.COLUMN_FATHER_NAME, userAttendenceList.fathername)
        values.put(DBContract.UserAttendence.COLUMN_CONTACT_NUMBER, userAttendenceList.contactnumber)
        values.put(DBContract.UserAttendence.COLUMN_ADRESS, userAttendenceList.adress)
        values.put(DBContract.UserAttendence.COLUMN_BLOOD_GROUP, userAttendenceList.bloodgroup)
        values.put(DBContract.UserAttendence.COLUMN_SEMESTER, userAttendenceList.semester)
        values.put(DBContract.UserAttendence.COLUMN_SESSION, userAttendenceList.session)
        values.put(DBContract.UserAttendence.COLUMN_ATTENDENCE_DATE, userAttendenceList.attendencedate)
        values.put(DBContract.UserAttendence.COLUMN_STUDENT_PRESENT, userAttendenceList.present)
        values.put(DBContract.UserAttendence.COLUMN_STUDENT_LEAVE, userAttendenceList.leave)
        values.put(DBContract.UserAttendence.COLUMN_STUDENT_ABSENT, userAttendenceList.absent)


        if (update)
            db.update(DBContract.UserAttendence.TABLE_NAME_ATTENDENCE, values, DBContract.UserAttendence.COLUMN_STUDENT_ID + "=" + userAttendenceList.studentId, null)
        else
            db.insert(DBContract.UserAttendence.TABLE_NAME_ATTENDENCE, null, values)
        // values.put(DBContract.UserEntry.COLUMN_AGE, user.age)
        db.close()

        // Insert the new row, returning the primary key value of the new row
        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteUser(studentname: String, rollNumber: String, fatherName: String, contactNumber: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.UserEntry.COLUMN_STUDENT_NAME + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(studentname, rollNumber, fatherName, contactNumber)
        // Issue SQL statement.
        db.delete(DBContract.UserEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readUser(studentname: String = "", session: String = "", semester: String = "", date: String = ""): ArrayList<UserModel> {
        val users = ArrayList<UserModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {

            if (session.isNotEmpty() && semester.isNotEmpty()) {
                cursor = db.rawQuery("SELECT * FROM " + DBContract.UserEntry.TABLE_NAME + " WHERE "
                        + DBContract.UserEntry.COLUMN_SESSION + "='" + session + "' AND " + DBContract.UserEntry.COLUMN_SEMESTER + "='" + semester + "'"
                        , null)
            } else {
                cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME + " WHERE "
                        + DBContract.UserEntry.COLUMN_STUDENT_NAME + "='" + studentname + "'"
                        , null)
            }

        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                users.add(UserModel(cursor))
                cursor.moveToNext()
            }
        }
        return users
    }

    fun getProfilesCount(): Long {
        val db = this.readableDatabase
        val count = DatabaseUtils.queryNumEntries(db, DBContract.UserAttendence.TABLE_NAME_ATTENDENCE)
        db.close()
        return count
    }

    fun deleteStudents(id: Int) {
        val db = this.writableDatabase
        db.delete(DBContract.UserEntry.TABLE_NAME, DBContract.UserEntry.COLUMN_STUDENT_ID + "=" + id, null) > 0
        db.close()
    }

    fun readAttendenceUser(studentname: String = "", session: String = "", semester: String = "", date: String = ""): ArrayList<UserAttendenceModel> {
        val db = writableDatabase
        var usersattendence = ArrayList<UserAttendenceModel>()
        var cursor: Cursor? = null
        try {
            if (date.isNotEmpty()) {
                cursor = db.rawQuery("SELECT * FROM " + DBContract.UserAttendence.TABLE_NAME_ATTENDENCE + " WHERE ("
                        + DBContract.UserAttendence.COLUMN_SESSION + "='" + session + "' AND " + DBContract.UserAttendence.COLUMN_SEMESTER + "='" + semester
                        + "') AND (" + DBContract.UserAttendence.COLUMN_ATTENDENCE_DATE + "='$date')", null)
                /*  cursor = db.rawQuery("SELECT * FROM " + DBContract.UserAttendence.TABLE_NAME_ATTENDENCE + " WHERE ("
                          *//*  + DBContract.UserAttendence.COLUMN_SESSION + "='" + session + "' AND " + DBContract.UserAttendence.COLUNN_SEMESTER + "='" + semester
                          + "') AND (" *//* + DBContract.UserAttendence.COLUMN_ATTENDENCE_DATE + "='" + date + "')", null)*/
            }
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ATEENDENCE)
            return ArrayList()
        }

        if (cursor!!.moveToFirst()) {
            while (cursor?.isAfterLast == false) {
                cursor?.let { UserAttendenceModel(it) }?.let { usersattendence.add(it) }
                cursor?.moveToNext()
            }
        }
        return usersattendence
    }

    fun readAllUsers(search: String): ArrayList<UserModel> {
        val users = ArrayList<UserModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            if (search.isNotEmpty()) {
                cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME + " WHERE "
                        + DBContract.UserEntry.COLUMN_STUDENT_NAME + " LIKE '%" + search + "%'", null)
            } else
                cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        // var age: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                users.add(UserModel(cursor))
                cursor.moveToNext()
            }
        }
        return users
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "FeedReaders.db"

        private val SQL_CREATE_ENTRIES = "CREATE TABLE " + DBContract.UserEntry.TABLE_NAME + " (" +
                DBContract.UserEntry.COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContract.UserEntry.COLUMN_STUDENT_NAME + " TEXT ," +
                DBContract.UserEntry.COLUMN_REGISTER_NUMBER + " TEXT," +
                DBContract.UserEntry.COLUMN_DATE_OF_BIRTH + " TEXT," +
                DBContract.UserEntry.COLUMN_FATHER_NAME + " TEXT," +
                DBContract.UserEntry.COLUMN_CONTACT_NUMBER + " TEXT," +
                DBContract.UserEntry.COLUMN_ADRESS + " TEXT," +
                DBContract.UserEntry.COLUMN_BLOOD_GROUP + " TEXT," +
                DBContract.UserEntry.COLUMN_SEMESTER + " TEXT," +
                DBContract.UserEntry.COLUMN_SESSION + " TEXT)"

        private val SQL_CREATE_ATEENDENCE = "CREATE TABLE " + DBContract.UserAttendence.TABLE_NAME_ATTENDENCE + " (" +
                DBContract.UserAttendence.COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContract.UserAttendence.COLUMN_STUDENT_NAME + " TEXT ," +
                DBContract.UserAttendence.COLUMN_REGISTER_NUMBER + " TEXT," +
                DBContract.UserAttendence.COLUMN_DATE_OF_BIRTH + " TEXT," +
                DBContract.UserAttendence.COLUMN_FATHER_NAME + " TEXT," +
                DBContract.UserAttendence.COLUMN_CONTACT_NUMBER + " TEXT," +
                DBContract.UserAttendence.COLUMN_ADRESS + " TEXT," +
                DBContract.UserAttendence.COLUMN_BLOOD_GROUP + " TEXT," +
                DBContract.UserAttendence.COLUMN_SEMESTER + " TEXT," +
                DBContract.UserAttendence.COLUMN_SESSION + " TEXT," +
                DBContract.UserAttendence.COLUMN_ATTENDENCE_DATE + " TEXT ," +/*
                DBContract.UserAttendence.COLUMN_STUDENT_TOTAL_STRENGTH + " INTEGER," +
                DBContract.UserAttendence.COLUMN_STUDENT_TOTAL_PRESENT + " INTEGER," +
                DBContract.UserAttendence.COLUMN_STUDENT_TOTAL_ABSENT + " INTEGER," +*/
                DBContract.UserAttendence.COLUMN_STUDENT_PRESENT + " TEXT," +
                DBContract.UserAttendence.COLUMN_STUDENT_LEAVE + " TEXT," +
                DBContract.UserAttendence.COLUMN_STUDENT_ABSENT + " TEXT)"

        //  DBContract.UserEntry.COLUMN_AGE + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME
        private val SQL_DELETE_ATTENDENCE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.UserAttendence.TABLE_NAME_ATTENDENCE
    }


}