package com.example.quizapp.activites.activites.utils

import com.example.quizapp.R

object IconPicker {
    val icons =
        arrayOf(
            R.drawable.icon_1,
            R.drawable.icon_2,
            R.drawable.icon_3,
            R.drawable.icon_4,
            R.drawable.icon_5,
            R.drawable.icon_6,
            R.drawable.icon_7,
            R.drawable.icon_8,
        )
    var currentIcons = 0

    fun getIcon(): Int {
        currentIcons = (currentIcons + 1) % icons.size
        return icons[currentIcons]
    }
}