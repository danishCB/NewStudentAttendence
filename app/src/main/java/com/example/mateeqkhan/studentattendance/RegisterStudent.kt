package com.example.mateeqkhan.studentattendance

import android.app.DatePickerDialog
import android.content.Context
import android.hardware.input.InputManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register_student.*
import kotlinx.android.synthetic.main.activity_register_student.view.*
import kotlinx.android.synthetic.main.activity_take_attendance.*
import kotlinx.android.synthetic.main.activity_view_students.*
import java.util.*

class RegisterStudent : AppCompatActivity() {

   // private var dateOfBirth: EditText? = null
    internal lateinit var datePickerDialog: DatePickerDialog
    lateinit var usersDBHelper: UserDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_student)

        title = "Register Student"
        this.button_add_user.isEnabled = false

        usersDBHelper = UserDBHelper(this)

        dateOfBirth.setOnClickListener {
            this.hideKeyboard()
            var calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var day = calendar.get(Calendar.DAY_OF_MONTH)
            datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, year, month, day -> dateOfBirth!!.setText(day.toString() + "-" + month + "-" + year) }, year, month, day)
            datePickerDialog.show()
        }


        selectBloodGroup.setOnClickListener {
            this.hideKeyboard()

            val customAlert = CustomListAlert(this, arrayOf("A+", "O+", "B+", "AB+", "A-", "O-", "B-", "AB-"), "Select Blood Group")
            customAlert.showCustomAlert { resultValue ->
                //  blood.text = resultValue as Editable?
                selectBloodGroup.setText(resultValue)
            }
        }

        selectSemester.setOnClickListener {
            this.hideKeyboard()

            val customAlert = CustomListAlert(this, arrayOf("Semseter 1", "Semseter 2", "Semseter 3", "Semseter 4", "Semseter 5", "Semseter 6", "Semseter 7", "Semseter 8"), "Select Semester ")
            customAlert.showCustomAlert { resultValue ->
                selectSemester.setText(resultValue)
            }
        }


        selectSession.setOnClickListener {
            this.hideKeyboard()
            val customAlert = CustomListAlert(this, arrayOf("2011-15", "2012-16", "2013-17", "2014-18", "2015-19", "2016-20", "2017-21", "2018-22", "2019-23", "2020-24", "2021-25", "2022-26", "2023-27", "2024-28", "2025-29", "2026-30"), "Select Section")
            customAlert.showCustomAlert { resultValue ->
                selectSession.setText(resultValue)
            }
        }

    }
        fun addUser(v: View) {
            this.hideKeyboard()

            var studentName = this.studentName.text.toString()
            var rollNumber = this.rollNumber.text.toString()
            var dateOfBirth = this.dateOfBirth!!.text.toString()
            var fatherName = this.fatherName.text.toString()
            var contactNumber = this.contactNumber.text.toString()
            var adress = this.adress.text.toString()
            var bloodGroup = this.selectBloodGroup.text.toString()
            var semester = this.selectSemester.text.toString()
            var session = this.selectSession.text.toString()

            //  var dateOfBirth = this.dateOfBirth.text.toString()
            usersDBHelper.insertUser(UserModel(studentname = studentName,dateOfBirth = dateOfBirth, rollnumber = rollNumber, fathername = fatherName, contactnumber = contactNumber, adress = adress, bloodgroup = bloodGroup, semester = semester, session = session))

            //clear all edittext s
            this.dateOfBirth!!.setText("")
            this.studentName.setText("")
            this.rollNumber.setText("")
            this.fatherName.setText("")
            this.contactNumber.setText("")
            this.adress.setText("")
            this.selectBloodGroup.text = "Select Blood Group"
            this.selectSemester.text = "Select Semester"
            this.selectSession.text = "Select Section"


            Toast.makeText(applicationContext, "Successfully registered.", Toast.LENGTH_SHORT).show()

        }

    fun hideKeyboard() {
        this.validateFields()
        val inputManager:InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.SHOW_FORCED)
    }

    fun validateFields() {
        if (this.studentName.text.isEmpty() || this.fatherName.text.isEmpty() || this.dateOfBirth.text.isEmpty() || this.rollNumber.text.isEmpty() ||
                this.contactNumber.text.isEmpty() || this.selectBloodGroup.text.isEmpty()
                        || this.selectSemester.text.isEmpty() || this.selectSession.text.isEmpty()) {
            this.button_add_user.isEnabled = false
        } else {
            this.button_add_user.isEnabled = true
        }
    }

}