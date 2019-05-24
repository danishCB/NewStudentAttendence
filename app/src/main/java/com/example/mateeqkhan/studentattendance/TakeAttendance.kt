package com.example.mateeqkhan.studentattendance

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlinx.android.synthetic.main.activity_take_attendance.*


class TakeAttendance : AppCompatActivity() {

    private var filteredStudents = ArrayList<UserAttendenceModel>()
    lateinit var usersDBHelper: UserDBHelper
    var semesterList = arrayOf("Semseter 1", "Semseter 2", "Semseter 3", "Semseter 4", "Semseter 5", "Semseter 6", "Semseter 7", "Semseter 8")
    var semesterYears = arrayOf("2011-15", "2012-16", "2013-17", "2014-18", "2015-19", "2016-20", "2017-21", "2018-22", "2019-23", "2020-24", "2021-25", "2022-26", "2023-27", "2024-28", "2025-29", "2026-30")
    var saveAttendenceInfo: HashMap<Integer, String> = HashMap()
    var userAttendenceModel: UserAttendenceModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_attendance)

        intialize()
        setListener()
    }

    private fun intialize() {
        title = "Class Attendance"
        usersDBHelper = UserDBHelper(this)
        setupCalenderView()
    }

    private fun setListener() {
        slcsmstr.setOnClickListener {
            val customAlert = CustomListAlert(this, semesterList, "Select Semester ")
            customAlert.showCustomAlert { resultValue ->
                //  blood.text = resultValue as Editable?
                slcsmstr.setText(resultValue)
            }
        }

        slccls.setOnClickListener {
            val customAlert = CustomListAlert(this, semesterYears, "Select Section")
            customAlert.showCustomAlert { resultValue ->
                //  blood.text = resultValue as Editable?
                slccls.setText(resultValue)
            }
        }

        saveAttendence.setOnClickListener {
            for (i in 0 until filteredStudents.size) {
                usersDBHelper.insertUserAttendence(filteredStudents[i])
            }
        }
    }


    fun filterStudentList() {

        var students: ArrayList<UserAttendenceModel> = usersDBHelper.readAttendenceUser("", slccls.text.toString(), slcsmstr.text.toString(), edtdat.text.toString())
        var student: ArrayList<UserModel> = ArrayList()
        if (students == null || students.size == 0) {
            student = usersDBHelper.readUser("", slccls.text.toString(), slcsmstr.text.toString(), edtdat.text.toString())
            var studentAdapter = StudentAdapter(student, this)
            lvNotes.adapter = studentAdapter
        } else {
            var studentAdapter = StudentAdapter(this, students)
            lvNotes.adapter = studentAdapter
        }
        if (students.isEmpty() && student.isEmpty()) {
            Toast.makeText(applicationContext, "No user found", Toast.LENGTH_SHORT).show()
            attendenceText.visibility = View.VISIBLE
        } else {
            lvNotes.visibility = View.VISIBLE
            println(students)
            // Toast.makeText(applicationContext, "Name " + students[0].studentname + "RollNumber" + students[0].rollnumber + "fathername" + students[0].fathername  + "contactnumber" + students[0].contactnumber + "adress" + students[0].adress + "bloodgroup" + students[0].bloodgroup + "semester" + students[0].semester + "session" + students[0].session, Toast.LENGTH_LONG).show()
        }
    }

    fun setupCalenderView() {

        val calendarView = findViewById<View>(R.id.calendarView) as MaterialCalendarView
        calendarView.setOnDateChangedListener { mateialView, calenderDay, flag ->
            if (slcsmstr.text == "Select Semester" && slccls.text == "Select Session") {
                calendarView.selectedDate = null
                Toast.makeText(applicationContext, "Please select the class detail first", Toast.LENGTH_LONG).show()

            } else {
                edtdat.setText(calenderDay.date.toString())
                filteredStudents.removeAll(filteredStudents)
                filterStudentList()
                Toast.makeText(applicationContext, "calenderDay: $calenderDay", Toast.LENGTH_LONG).show()
            }
        }
    }

    inner class StudentAdapter : BaseAdapter {

        private var studentList = ArrayList<UserModel>()
        private var studentListAttendence = ArrayList<UserAttendenceModel>()
        private var context: Context? = null

        constructor(notesList: ArrayList<UserModel>, context: Context) : super() {
            this.studentList = notesList
            this.context = context
        }

        constructor(context: Context, notesListAttendence: ArrayList<UserAttendenceModel>) : super() {
            this.studentListAttendence = notesListAttendence
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

            if (studentListAttendence.size != 0) {
                viewHolder.name.text = studentListAttendence[position].studentname
                viewHolder.studentRollNumber.text = studentListAttendence[position].rollnumber
                viewHolder.studentSemester.text = studentListAttendence[position].semester
                viewHolder.studentSession.text = studentListAttendence[position].session

            } else {
                viewHolder.name.text = studentList[position].studentname
                viewHolder.studentRollNumber.text = studentList[position].rollnumber
                viewHolder.studentSemester.text = studentList[position].semester
                viewHolder.studentSession.text = studentList[position].session
            }

            // if (viewHolder.chk1.isChecked || viewHolder.chk2.isChecked || viewHolder.chk3.isChecked) {

            if (studentListAttendence.size != 0) {
                for (i in 0 until studentList.size) {
                    userAttendenceModel = UserAttendenceModel()
                    userAttendenceModel?.studentId = studentListAttendence[i].studentId
                    userAttendenceModel?.semester = studentListAttendence[i].semester
                    userAttendenceModel?.dateOfBirth = studentListAttendence[i].dateOfBirth
                    userAttendenceModel?.studentname = studentListAttendence[i].studentname
                    userAttendenceModel?.fathername = studentListAttendence[i].fathername
                    userAttendenceModel?.contactnumber = studentListAttendence[i].contactnumber
                    userAttendenceModel?.adress = studentListAttendence[i].adress
                    userAttendenceModel?.attendencedate = edtdat.text.toString()
                    userAttendenceModel?.bloodgroup = studentListAttendence[i].bloodgroup
                    userAttendenceModel?.session = studentListAttendence[i].session
                    userAttendenceModel?.session = studentListAttendence[i].session
                    userAttendenceModel?.present = ""
                    userAttendenceModel?.absent = ""
                    userAttendenceModel?.leave = ""
                    userAttendenceModel?.let {
                        filteredStudents.add(it)
//                    filteredStudents.removeAll(filteredStudents)
                    }
                }
            } else {
                if (studentList.size != filteredStudents.size) {
                    for (i in 0 until studentList.size) {
                        userAttendenceModel = UserAttendenceModel()
                        userAttendenceModel?.studentId = studentList[i].studentId
                        userAttendenceModel?.semester = studentList[i].semester
                        userAttendenceModel?.dateOfBirth = studentList[i].dateOfBirth
                        userAttendenceModel?.studentname = studentList[i].studentname
                        userAttendenceModel?.fathername = studentList[i].fathername
                        userAttendenceModel?.contactnumber = studentList[i].contactnumber
                        userAttendenceModel?.adress = studentList[i].adress
                        userAttendenceModel?.attendencedate = edtdat.text.toString()
                        userAttendenceModel?.bloodgroup = studentList[i].bloodgroup
                        userAttendenceModel?.session = studentList[i].session
                        userAttendenceModel?.session = studentList[i].session
                        userAttendenceModel?.present = ""
                        userAttendenceModel?.absent = ""
                        userAttendenceModel?.leave = ""
                        userAttendenceModel?.let {
                            filteredStudents.add(it)
//                    filteredStudents.removeAll(filteredStudents)
                        }
                    }
                }
            }
            //}

            viewHolder.chk1.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    viewHolder.chk2.isChecked = false
                    viewHolder.chk3.isChecked = false
                    filteredStudents[position].present = "Present"
                    filteredStudents[position].absent = ""
                    filteredStudents[position].leave = ""
                } else
                    viewHolder.chk1.isChecked = false
                //filteredStudents.remove(filteredStudents[position])
                // notifyDataSetChanged()
            }
            viewHolder.chk2.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    viewHolder.chk1.isChecked = false
                    viewHolder.chk3.isChecked = false
                    filteredStudents[position].leave = "Leave"
                    filteredStudents[position].absent = ""
                    filteredStudents[position].present = ""
                } else
                    viewHolder.chk2.isChecked = false
                //filteredStudents.remove(filteredStudents[position])
                // notifyDataSetChanged()
            }
            viewHolder.chk3.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    viewHolder.chk1.isChecked = false
                    viewHolder.chk2.isChecked = false
                    filteredStudents[position].leave = ""
                    filteredStudents[position].present = ""
                    filteredStudents[position].absent = "Absent"
                } else
                    viewHolder.chk3.isChecked = false
                // filteredStudents.remove(filteredStudents[position])
                //  notifyDataSetChanged()
            }


//            vh.chk1.text = notesList[position].content

            return view
        }

        override fun getItem(position: Int): Any {
            if (studentListAttendence.size != 0) {
                return studentListAttendence[position]
            } else {
                return studentList[position]
            }
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            if (studentListAttendence.size != 0) {
                return studentListAttendence.size
            } else {
                return studentList.size
            }
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
            name = view?.findViewById<TextView>(R.id.studentName) as TextView
            studentRollNumber = view?.findViewById<TextView>(R.id.rollNumber) as TextView
            studentSemester = view?.findViewById<TextView>(R.id.studentSemester) as TextView
            studentSession = view?.findViewById<TextView>(R.id.studentSession) as TextView
            chk1 = view?.findViewById<TextView>(R.id.chk1) as CheckBox
            chk2 = view?.findViewById<TextView>(R.id.chk2) as CheckBox
            chk3 = view?.findViewById<TextView>(R.id.chk3) as CheckBox


        }

    }
}












