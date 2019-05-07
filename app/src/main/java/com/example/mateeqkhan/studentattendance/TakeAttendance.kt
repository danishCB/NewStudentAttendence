package com.example.mateeqkhan.studentattendance

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.SparseBooleanArray
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlinx.android.synthetic.main.activity_take_attendance.*
//import kotlinx.android.synthetic.main.activity_register_student.*
import kotlinx.android.synthetic.main.activity_take_attendance.*
import kotlinx.android.synthetic.main.view_list.*
//import kotlinx.android.synthetic.main.activity_register_student.*
import java.lang.StringBuilder


class TakeAttendance : AppCompatActivity() {

    private var filteredStudents = ArrayList<UserModel>()
    lateinit var usersDBHelper: UserDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_attendance)

        title = "Class Attendance"
        usersDBHelper = UserDBHelper(this)
        setupCalenderView()


        slcsmstr.setOnClickListener {
            val customAlert = CustomListAlert(this, arrayOf("Semseter 1", "Semseter 2", "Semseter 3", "Semseter 4", "Semseter 5", "Semseter 6", "Semseter 7", "Semseter 8"), "Select Semester ")
            customAlert.showCustomAlert { resultValue ->
                //  blood.text = resultValue as Editable?
                slcsmstr.setText(resultValue)
            }
        }

        slccls.setOnClickListener {
            val customAlert = CustomListAlert(this, arrayOf("2011-15", "2012-16", "2013-17", "2014-18", "2015-19", "2016-20", "2017-21", "2018-22", "2019-23", "2020-24", "2021-25", "2022-26", "2023-27", "2024-28", "2025-29", "2026-30"), "Select Section")
            customAlert.showCustomAlert { resultValue ->
                //  blood.text = resultValue as Editable?
                slccls.setText(resultValue)
            }
        }




//        lvNotes.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
//            Toast.makeText(this, "Click on " + selectedStudent[position].name, Toast.LENGTH_SHORT).show()
//        }



    }

    fun filterStudentList() : ArrayList<UserModel> {



        var students = usersDBHelper.readUser("", slccls.text.toString(), slcsmstr.text.toString())
        var studentAdapter = StudentAdapter(this, students)
        lvNotes.adapter = studentAdapter
        if (students.isEmpty()) {
            Toast.makeText(applicationContext, "No user found", Toast.LENGTH_SHORT).show()
        } else {
            println(students)
           // Toast.makeText(applicationContext, "Name " + students[0].studentname + "RollNumber" + students[0].rollnumber + "fathername" + students[0].fathername  + "contactnumber" + students[0].contactnumber + "adress" + students[0].adress + "bloodgroup" + students[0].bloodgroup + "semester" + students[0].semester + "session" + students[0].session, Toast.LENGTH_LONG).show()
        }

        return students

    }


    fun setupCalenderView() {

        val calendarView = findViewById<View>(R.id.calendarView) as MaterialCalendarView
        calendarView.setOnDateChangedListener { mateialView, calenderDay, flag ->
            if (slcsmstr.text == "Select Semester" && slccls.text == "Select Session") {
                calendarView.selectedDate = null
                Toast.makeText(applicationContext, "Please select the class detail first", Toast.LENGTH_LONG).show()

            } else {
                filterStudentList()
                Toast.makeText(applicationContext, "calenderDay: $calenderDay", Toast.LENGTH_LONG).show()
            }
        }

    }

    inner class StudentAdapter : BaseAdapter {

        private var studentList = ArrayList<UserModel>()
        private var context: Context? = null

        constructor(context: Context, notesList: ArrayList<UserModel>) : super() {
            this.studentList = notesList
            this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

            val view: View?
            val viewHolder: ViewHolder

            val any = if (convertView == null) {
                view = layoutInflater.inflate(R.layout.note, parent, false)
                viewHolder = ViewHolder(view)
                view!!.tag = viewHolder
                Log.i("JSA", "set Tag for ViewHolder,  position: " + position)
            } else {
                view = convertView
                viewHolder = view.tag as ViewHolder
            }

            viewHolder.name.text = studentList[position].studentname
            viewHolder.studentRollNumber.text = studentList[position].rollnumber
            viewHolder.studentSemester.text = studentList[position].semester
            viewHolder.studentSession.text = studentList[position].session
//            vh.chk1.text = notesList[position].content

            return view
        }

        override fun getItem(position: Int): Any {
            return studentList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return studentList.size
        }
    }

    private class ViewHolder(view: View?) {
        val name: TextView
        val studentRollNumber: TextView
        val studentSemester: TextView
        val studentSession: TextView
        val chk1: CheckBox
        val chk2: CheckBox
        val chk3: CheckBox

        init {
            this.name = view?.findViewById<TextView>(R.id.studentName) as TextView
            this.studentRollNumber = view?.findViewById<TextView>(R.id.rollNumber) as TextView
            this.studentSemester = view?.findViewById<TextView>(R.id.studentSemester) as TextView
            this.studentSession = view?.findViewById<TextView>(R.id.studentSession) as TextView
            this.chk1 = view?.findViewById<TextView>(R.id.chk1) as CheckBox
            this.chk2 = view?.findViewById<TextView>(R.id.chk2) as CheckBox
            this.chk3 = view?.findViewById<TextView>(R.id.chk3) as CheckBox
        }


    }
}












