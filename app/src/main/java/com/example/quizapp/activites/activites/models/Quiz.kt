package com.example.quizapp.activites.activites.models

data class Quiz(
    var Id: String=" ",
    var title: String=" ",
    var question: MutableMap<String,Question> = mutableMapOf()
)
