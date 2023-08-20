package com.example.quizapp.activites.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.quizapp.R
import com.google.firebase.auth.FirebaseAuth

class LoginIntro : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_intro)
        val auth = FirebaseAuth.getInstance()

        if(auth.currentUser!=null){
            redirect("MAIN")
            Toast.makeText(this,"Welcome",Toast.LENGTH_LONG).show()
        }


        val txtIntro=findViewById<TextView>(R.id.btnIntro)
        txtIntro.setOnClickListener {
            redirect("LOGIN")
        }

    }

    private fun redirect(name: String) {
        val intent = when (name) {
            "LOGIN" -> Intent(this, LoginActivity::class.java)
            "MAIN" -> Intent(this, MainActivity::class.java)
            else -> throw Exception("No path exist")
        }
        startActivity(intent)
        finish()
    }
}