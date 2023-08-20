package com.example.quizapp.activites.activites

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import com.example.quizapp.R
import com.example.quizapp.activites.activites.models.Quiz
import com.google.gson.Gson

class ResultActivity : AppCompatActivity() {

    lateinit var quiz: Quiz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        setUpViews()
    }

    private fun setUpViews() {
        val quizData = intent.getStringExtra("QUIZ")
        quiz = Gson().fromJson<Quiz>(quizData, Quiz::class.java)
        calculateScore()
        answerViews()
    }

    private fun answerViews() {
        val txtAnswer = findViewById<TextView>(R.id.quesAns)

        val builder = StringBuilder("")
        for (entry in quiz.question.entries) {
            val question = entry.value
            builder.append("Question: ${question.description}\n")
            builder.append("Answer: ${question.answer}\n\n")

            txtAnswer.text = builder.toString()
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            txtAnswer.text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT);
//        } else {
//            txtAnswer.text = Html.fromHtml(builder.toString());
//        }

    }

    private fun calculateScore() {
        var score = 0
        for (entry in quiz.question.entries) {
            val question = entry.value
            if (question.answer == question.userAnswer) {
                score += 10
            }
        }
        val result = findViewById<TextView>(R.id.resultTxt)
        result.text = "YOUR SCORE : $score"
    }
}