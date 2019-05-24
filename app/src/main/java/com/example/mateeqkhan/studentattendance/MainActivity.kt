package com.example.mateeqkhan.studentattendance

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Home"

        val studententry: Button = findViewById(R.id.studententry)
        val viewstudent: Button = findViewById(R.id.viewstudent)
        val takeattendance: Button = findViewById(R.id.takeattendance)

        studententry.setOnClickListener {
            val intent = Intent(this, RegisterStudent::class.java)
            startActivity(intent)
        }

        viewstudent.setOnClickListener {
            val intent = Intent(this, ViewStudents::class.java)
            startActivity(intent)
        }

        takeattendance.setOnClickListener {
            val intent = Intent(this, TakeAttendance::class.java)
            startActivity(intent)
        }


    }
}
