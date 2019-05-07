package com.example.mateeqkhan.studentattendance

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.util.ArrayList

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
class UserDBHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertUser(user: UserModel): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
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

        // values.put(DBContract.UserEntry.COLUMN_AGE, user.age)

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContract.UserEntry.TABLE_NAME, null, values)

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

    fun readUser(studentname: String= "", session: String = "", semester: String = ""): ArrayList<UserModel> {
        val users = ArrayList<UserModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            if (session.isNotEmpty() && semester.isNotEmpty()) {

                cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME + " WHERE "
                        + DBContract.UserEntry.COLUMN_SESSION + "='" + session + "AND" + DBContract.UserEntry.COLUNN_SEMESTER + "='" + semester + "'"
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

        var studentname: String
        var rollnumber: String
        var dateOfBirth: String
        var fathername: String
        var contactnumber: String
        var adress: String
        var bloodgroup: String
        var semester: String
        var session: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                studentname = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_STUDENT_NAME))
                rollnumber = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_REGISTER_NUMBER))
                dateOfBirth = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUNN_DATE_OF_BIRTH))
                fathername = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_FATHER_NAME))
                contactnumber = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_CONTACT_NUMBER))
                adress = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_ADRESS))
                bloodgroup = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_BLOOD_GROUP))
                semester = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUNN_SEMESTER))
                session = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_SESSION))





                users.add(UserModel(studentname, rollnumber, dateOfBirth, fathername, contactnumber, adress, bloodgroup, semester, session))
                cursor.moveToNext()
            }
        }
        return users
    }

    fun readAllUsers(): ArrayList<UserModel> {
        val users = ArrayList<UserModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var studentname: String
        var rollnumber: String
        var dateOfBirth: String
        var fathername: String
        var contactnumber: String
        var adress: String
        var bloodgroup: String
        var semester: String
        var session: String

        // var age: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                studentname = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_STUDENT_NAME))
                rollnumber  = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_REGISTER_NUMBER))
                dateOfBirth = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUNN_DATE_OF_BIRTH))
                fathername = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_FATHER_NAME))
                contactnumber = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_CONTACT_NUMBER))
                adress = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_ADRESS))
                bloodgroup = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_BLOOD_GROUP))
                semester = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUNN_SEMESTER))
                session = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_SESSION))


                users.add(UserModel(studentname, rollnumber, dateOfBirth,fathername, contactnumber, adress, bloodgroup, semester, session))
                cursor.moveToNext()
            }
        }
        return users
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "FeedReader.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DBContract.UserEntry.TABLE_NAME + " (" +
                        DBContract.UserEntry.COLUMN_STUDENT_NAME + " TEXT PRIMARY KEY," +
                        DBContract.UserEntry.COLUMN_REGISTER_NUMBER + " TEXT," +
                        DBContract.UserEntry.COLUNN_DATE_OF_BIRTH + " TEXT," +
                        DBContract.UserEntry.COLUMN_FATHER_NAME + " TEXT," +
                        DBContract.UserEntry.COLUMN_CONTACT_NUMBER + " TEXT," +
                        DBContract.UserEntry.COLUMN_ADRESS + " TEXT," +
                        DBContract.UserEntry.COLUMN_BLOOD_GROUP + " TEXT," +
                        DBContract.UserEntry.COLUNN_SEMESTER + " TEXT," +
                        DBContract.UserEntry.COLUMN_SESSION + " TEXT)"





                        //  DBContract.UserEntry.COLUMN_AGE + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME
    }
}