package com.example.quizapp.activites.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.quizapp.R
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        firebaseAuth = FirebaseAuth.getInstance()

        val userEmail = findViewById<TextView>(R.id.userEmail)
        userEmail.text = firebaseAuth.currentUser?.email


        val logOut = findViewById<Button>(R.id.logOut)
        logOut.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}