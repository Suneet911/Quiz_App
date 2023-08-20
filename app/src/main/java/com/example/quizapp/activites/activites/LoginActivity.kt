package com.example.quizapp.activites.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.quizapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth=Firebase.auth

        val txtBtn=findViewById<TextView>(R.id.txtBtn)
        txtBtn.setOnClickListener {
            val intent=Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }


        val btnLogIn=findViewById<Button>(R.id.btnLogin)
        btnLogIn.setOnClickListener {
            logIn()
        }

    }
    private fun logIn(){
        val email=findViewById<EditText>(R.id.etEmailAddress).text.toString()
        val password=findViewById<EditText>(R.id.etPassword).text.toString()

        if(email.isBlank() || password.isBlank()){
            Toast.makeText(this,"Email/Password cannot be empty",Toast.LENGTH_LONG).show()
            return
        }


        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    val intent= Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Welcome",Toast.LENGTH_LONG).show()
                    finish()
                }
                else{
                    Toast.makeText(this,"Authentication Failed",Toast.LENGTH_LONG).show()
                }
            }
    }

    fun btnSignUp(view: View) {}
}