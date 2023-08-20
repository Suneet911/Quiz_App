package com.example.quizapp.activites.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.quizapp.R
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        firebaseAuth = FirebaseAuth.getInstance()

        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        btnSignUp.setOnClickListener {
            signUp()
        }

        val etAlreadySignIn=findViewById<TextView>(R.id.etAlreadySignIn)
        etAlreadySignIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signUp() {
        val email = findViewById<EditText>(R.id.etEmailAddresss).text.toString()
        val password = findViewById<EditText>(R.id.etPasswords).text.toString()
        val cnfPassword = findViewById<EditText>(R.id.cnfPasswords).text.toString()


        if (email.isBlank() || password.isBlank() || cnfPassword.isBlank()) {
            Toast.makeText(this, "Email/Password cannot be blank.", Toast.LENGTH_LONG).show()
            return
        }
        if (password != cnfPassword) {
            Toast.makeText(this, "Password do not match", Toast.LENGTH_LONG).show()
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Sign Up successful", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error creating user", Toast.LENGTH_SHORT).show()
                }
            }
    }
}