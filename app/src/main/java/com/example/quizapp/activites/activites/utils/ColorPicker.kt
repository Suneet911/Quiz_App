package com.example.quizapp.activites.activites.utils

object ColorPicker {
    val colors =
        arrayOf(
            "#FA8334",
            "#7BBD7A",
            "#388697",
            "#FFE882",
            "#271033",
            "#93FF82",
            "#FF8293"
        )
    var currentcolor = 0

    fun getColor(): String {
        currentcolor = (currentcolor + 1) % colors.size
        return colors[currentcolor]
    }
}