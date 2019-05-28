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
    private var studentListAttendence = ArrayList<UserAttendenceModel>()
    private var studentList = ArrayList<UserModel>()


    var semesterList = arrayOf("Semseter 1", "Semseter 2", "Semseter 3", "Semseter 4", "Semseter 5", "Semseter 6", "Semseter 7", "Semseter 8")
    var semesterYears = arrayOf("2011-15", "2012-16", "2013-17", "2014-18", "2015-19", "2016-20", "2017-21", "2018-22", "2019-23", "2020-24", "2021-25", "2022-26", "2023-27", "2024-28", "2025-29", "2026-30")
    var saveAttendenceInfo: HashMap<Integer, String> = HashMap()
    var userAttendenceModel: UserAttendenceModel? = null
    var totalPresent = 0
    var totalAbsent = 0
    var totalLeave = 0

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
                if (studentListAttendence.size != 0)
                    usersDBHelper.insertUserAttendence(filteredStudents[i], true)
                else
                    usersDBHelper.insertUserAttendence(filteredStudents[i], false)
            }
            finish()
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
            saveAttendence.setText("Update")
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
                studentListAttendence.removeAll(studentListAttendence)
                studentList.removeAll(studentList)
                filteredStudents.removeAll(filteredStudents)
                totalPresent = 0
                totalLeave = 0
                totalAbsent = 0
                filterStudentList()
                Toast.makeText(applicationContext, "calenderDay: $calenderDay", Toast.LENGTH_LONG).show()
            }
        }
    }

    inner class StudentAdapter : BaseAdapter {

        /*
                private var studentListAttendence = ArrayList<UserAttendenceModel>()
        */
        private var context: Context? = null

        constructor(notesList: ArrayList<UserModel>, context: Context) : super() {
            studentList = notesList
            this.context = context
        }

        constructor(context: Context, notesListAttendence: ArrayList<UserAttendenceModel>) : super() {
            studentListAttendence = notesListAttendence
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

            var count = usersDBHelper.getProfilesCount()

            edtstr.setText(getTotalStrength().toString())
            getAttendenceInfo()

            if (studentListAttendence.size != 0) {
                viewHolder.name.text = studentListAttendence[position].studentname
                viewHolder.studentRollNumber.text = studentListAttendence[position].rollnumber
                viewHolder.studentSemester.text = studentListAttendence[position].semester
                viewHolder.studentSession.text = studentListAttendence[position].session
                if (studentListAttendence[position].absent == "Absent") {
                    viewHolder.presentCheck.isChecked = false
                    viewHolder.leaveCheck.isChecked = false
                    viewHolder.absentCheck.isChecked = true
                    //    totalAbsent = totalAbsent + 1
                } else if (studentListAttendence[position].present == "Present") {
                    viewHolder.presentCheck.isChecked = true
                    viewHolder.leaveCheck.isChecked = false
                    viewHolder.absentCheck.isChecked = false
                    //  totalPresent = totalPresent + 1
                } else {
                    viewHolder.presentCheck.isChecked = false
                    viewHolder.leaveCheck.isChecked = true
                    viewHolder.absentCheck.isChecked = false
                    //  totalLeave = totalLeave + 1
                }

            } else {
                viewHolder.name.text = studentList[position].studentname
                viewHolder.studentRollNumber.text = studentList[position].rollnumber
                viewHolder.studentSemester.text = studentList[position].semester
                viewHolder.studentSession.text = studentList[position].session
                viewHolder.presentCheck.isChecked = false
                viewHolder.leaveCheck.isChecked = false
                viewHolder.absentCheck.isChecked = false
            }

/*
            edtprst.setText(totalPresent.toString())
            editText2.setText(totalAbsent.toString() + "/" + totalLeave.toString())
*/

            // if (viewHolder.presentCheck.isChecked || viewHolder.leaveCheck.isChecked || viewHolder.absentCheck.isChecked) {

            if (studentListAttendence.size != 0 && (studentListAttendence.size != filteredStudents.size)) {
                for (i in 0 until studentListAttendence.size) {
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

            viewHolder.presentCheck.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    viewHolder.leaveCheck.isChecked = false
                    viewHolder.absentCheck.isChecked = false
                    filteredStudents[position].present = "Present"
                    filteredStudents[position].absent = ""
                    filteredStudents[position].leave = ""
                    totalPresent = totalPresent + 1
                } else {
                    viewHolder.presentCheck.isChecked = false
                    totalPresent = totalPresent - 1
                }
                edtprst.setText(totalPresent.toString())
                //filteredStudents.remove(filteredStudents[position])
                // notifyDataSetChanged()
            }
            viewHolder.leaveCheck.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    viewHolder.presentCheck.isChecked = false
                    viewHolder.absentCheck.isChecked = false
                    filteredStudents[position].leave = "Leave"
                    filteredStudents[position].absent = ""
                    filteredStudents[position].present = ""
                    totalLeave = totalLeave + 1
                } else {
                    viewHolder.leaveCheck.isChecked = false
                    totalLeave = totalLeave - 1
                    //filteredStudents.remove(filteredStudents[position])
                    // notifyDataSetChanged()
                }
                editText2.setText(totalAbsent.toString() + "/" + totalLeave.toString())
            }
            viewHolder.absentCheck.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    viewHolder.presentCheck.isChecked = false
                    viewHolder.leaveCheck.isChecked = false
                    filteredStudents[position].leave = ""
                    filteredStudents[position].present = ""
                    filteredStudents[position].absent = "Absent"
                    totalAbsent = totalAbsent + 1
                } else {
                    viewHolder.absentCheck.isChecked = false
                    totalAbsent = totalAbsent - 1
                }// filteredStudents.remove(filteredStudents[position])
                //  notifyDataSetChanged()
                //    editText2.setText(totalAbsent.toString() + "/" + totalLeave.toString())
                editText2.setText(totalAbsent.toString() + "/" + totalLeave.toString())
            }


//            vh.presentCheck.text = notesList[position].content

            return view
        }

        fun getTotalStrength(): Int {
            if (studentList.size != 0)
                return studentList.size
            else
                return studentListAttendence.size
        }

        fun getAttendenceInfo() {
            for (i in 0 until studentListAttendence.size) {
                if (studentListAttendence[i].absent == "Absent") {
                    totalAbsent = totalAbsent + 1
                } else if (studentListAttendence[i].present == "Present") {
                    totalPresent = totalPresent + 1
                } else {
                    totalLeave = totalLeave + 1
                }
            }
            edtprst.setText(totalPresent.toString())
            editText2.setText(totalAbsent.toString() + "/" + totalLeave.toString())
        }

        fun addlist(list: ArrayList<Any>) {

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
        val presentCheck: CheckBox
        val leaveCheck: CheckBox
        val absentCheck: CheckBox

        init {
            name = view?.findViewById<TextView>(R.id.studentName) as TextView
            studentRollNumber = view?.findViewById<TextView>(R.id.rollNumber) as TextView
            studentSemester = view?.findViewById<TextView>(R.id.studentSemester) as TextView
            studentSession = view?.findViewById<TextView>(R.id.studentSession) as TextView
            presentCheck = view?.findViewById<TextView>(R.id.presentChk) as CheckBox
            leaveCheck = view?.findViewById<TextView>(R.id.leaveChk) as CheckBox
            absentCheck = view?.findViewById<TextView>(R.id.absentChk) as CheckBox


        }

    }
}












