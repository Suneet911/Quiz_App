package com.example.quizapp.activites.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.activites.activites.adapters.OptionAdapter
import com.example.quizapp.activites.activites.models.Question
import com.example.quizapp.activites.activites.models.Quiz
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class QuestionActivity : AppCompatActivity() {

    var quizzes: MutableList<Quiz>? = null
    var questions: MutableMap<String, Question>? = null
    var index = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        setUpFireStore()
        setUpEventListener()

    }

    private fun setUpEventListener() {
        val btnPrevious = findViewById<Button>(R.id.btnPrevious)
        btnPrevious.setOnClickListener {
            index--
            bindViews()
        }
        val btnNext = findViewById<Button>(R.id.btnNext)
        btnNext.setOnClickListener {
            index++
            bindViews()
        }
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            Log.d("FINAL", questions.toString())

            val intent=Intent(this,ResultActivity::class.java)
            val json=Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZ", json)
            startActivity(intent)
        }

    }


    private fun setUpFireStore() {
        val fireStore = FirebaseFirestore.getInstance()
        val date = intent.getStringExtra("DATE")
        if (date != null) {
            fireStore.collection("quizzes").whereEqualTo("title", date).get()
                .addOnSuccessListener {
                    Log.d("DATA", it.toObjects(Quiz::class.java).toString())
                    if (it != null && !it.isEmpty) {
                        quizzes = it.toObjects(Quiz::class.java)
                        questions = quizzes!![0].question
                        bindViews()
                    } else {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "No Quiz Available", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun bindViews() {

        val btnPrevious = findViewById<Button>(R.id.btnPrevious)
        btnPrevious.visibility = View.GONE
        val btnNext = findViewById<Button>(R.id.btnNext)
        btnNext.visibility = View.GONE
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        btnSubmit.visibility = View.GONE

        if (index == 1) {
            btnNext.visibility = View.VISIBLE
        } else if (index == questions!!.size) {
            btnPrevious.visibility = View.VISIBLE
            btnSubmit.visibility = View.VISIBLE
        } else {
            btnPrevious.visibility = View.VISIBLE
            btnNext.visibility = View.VISIBLE
        }

        val question = questions!!["question$index"]
        question?.let {
            val description = findViewById<TextView>(R.id.description)
            description.text = it.description
            val optionAdapter = OptionAdapter(this, it)
            val optionList = findViewById<RecyclerView>(R.id.optionList)
            optionList.layoutManager = LinearLayoutManager(this)
            optionList.adapter = optionAdapter
            optionList.setHasFixedSize(true)
        }

    }

}

