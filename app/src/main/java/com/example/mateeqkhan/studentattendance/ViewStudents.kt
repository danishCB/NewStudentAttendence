package com.example.mateeqkhan.studentattendance

import android.content.Context
import kotlinx.android.synthetic.main.activity_main.*

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_register_student.*
import kotlinx.android.synthetic.main.activity_view_students.*
import android.text.Editable
import android.text.TextWatcher


class ViewStudents : AppCompatActivity() {

    private var registeredStudent = ArrayList<UserModel>()
    lateinit var usersDBHelper: UserDBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_students)

        title = "View Student"

        usersDBHelper = UserDBHelper(this)
        registeredStudent = usersDBHelper.readAllUsers(searchbox.text.toString())
        var notesAdapter = NotesAdapter(this, registeredStudent)
        lvNotes.adapter = notesAdapter
        searchbox.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                registeredStudent = usersDBHelper.readAllUsers(searchbox.text.toString())
                notesAdapter.updatelist(registeredStudent)

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })


    }

    inner class NotesAdapter : BaseAdapter {

        private var students = ArrayList<UserModel>()
        private var context: Context? = null

        constructor(context: Context, notesList: ArrayList<UserModel>) : super() {
            this.students = notesList
            this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

            val view: View?
            val vh: ViewHolder

            val any = if (convertView == null) {
                view = layoutInflater.inflate(R.layout.view_list, parent, false)
                vh = ViewHolder(view)
                view!!.tag = vh
                Log.i("JSA", "set Tag for ViewHolder, position: " + position)
            } else {
                view = convertView
                vh = view.tag as ViewHolder
            }

            vh.studentName.text = students[position].studentname
            vh.fatherName.text = students[position].fathername
            vh.rollNumber.text = students[position].rollnumber
            vh.semester.text = students[position].semester
            vh.session.text = students[position].session
            vh.contactNumber.text = students[position].contactnumber
            return view
        }

        fun updatelist(list: ArrayList<UserModel>) {
            students.clear()
            students.addAll(list)
            this.notifyDataSetChanged()
        }

        override fun getItem(position: Int): Any {
            return students[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return students.size
        }
    }

    private class ViewHolder(view: View?) {
        val studentName: TextView
        val fatherName: TextView
        val rollNumber: TextView
        val semester: TextView
        val session: TextView
        val contactNumber: TextView

        init {
            this.studentName = view?.findViewById<EditText>(R.id.studentName) as TextView
            this.fatherName = view?.findViewById<TextView>(R.id.fatherName) as TextView
            this.rollNumber = view?.findViewById<TextView>(R.id.rollNumber) as TextView
            this.semester = view?.findViewById<TextView>(R.id.semester) as TextView
            this.session = view?.findViewById<TextView>(R.id.session) as TextView
            this.contactNumber = view?.findViewById<TextView>(R.id.contactNumber) as TextView

        }


    }


//    fun searchStudent(v: View) {
//        var users = usersDBHelper.readAllUsers()
//        this.ll_entries.removeAllViews()
//        users.forEach {
//            var tv_user = TextView(this)
//            tv_user.textSize = 30F
//            tv_user.text = "Name" + ":" + it.studentname.toString() +"\n"+"Roll nbr"+":"  + it.rollnumber.toString()+"\n" +"Father Name" + ":"+ it.fathername.toString()+"\n" +"Contact No" + ":" + it.contactnumber.toString()
//             //+ "-" + it.contactnumber.toString() +
//            this.ll_entries.addView(tv_user)
//        }
//        this.textview_result.text = "Fetched " + users.size + " users"
//    }
//
//    fun deleteUser(v: View) {
//        var studentName = this.studentName.text.toString()
//        var rollNumber = this.rollNumber.text.toString()
//        var fatherName = this.fatherName.text.toString()
//        var contactNumber = this.contactNumber.text.toString()
//
//        val result = usersDBHelper.deleteUser(studentName, rollNumber,  fatherName, contactNumber)
//       // val result = usersDBHelper.deleteUser(rollNumber)
//      // val result = usersDBHelper.deleteUser(fatherName)
//        //val result = usersDBHelper.deleteUser(contactNumber)
//        this.textview_result.text = "Deleted user : " + result
//        this.ll_entries.removeAllViews()
//    }


//    fun showAllUsers(v: View) {
//        var users = usersDBHelper.readAllUsers()
//        this.ll_entries.removeAllViews()
//        users.forEach {
//            var tv_user = TextView(this)
//            tv_user.textSize = 30F
//            tv_user.text = it.studentname.toString() + " - " + it.rollnumber.toString()
//            this.ll_entries.addView(tv_user)
//        }
//        this.textview_result.text = "Fetched " + users.size + " users"
//    }
//        fun searchStudent(v: View) {
//            var users = usersDBHelper.readUser(this.searchbox.text.toString())
//            if (users.isEmpty()) {
//              Toast.makeText(applicationContext, "No user found", Toast.LENGTH_SHORT).show()
//        } else {
//                println(users)
//                Toast.makeText(applicationContext, "Name " + users[0].studentname + "RollNumber" + users[0].rollnumber + "fathername" + users[0].fathername  + "contactnumber" + users[0].contactnumber + "adress" + users[0].adress + "bloodgroup" + users[0].bloodgroup + "semester" + users[0].semester + "session" + users[0].session, Toast.LENGTH_LONG).show()
//            }
//            }

}







