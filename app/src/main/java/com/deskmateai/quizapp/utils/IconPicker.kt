package com.deskmateai.quizapp.utils

import com.deskmateai.quizapp.R

object IconPicker{
    val icons = arrayOf(R.drawable.ic_test_icon, R.drawable.icon_2, R.drawable.icon_3, R.drawable.iicon_4, R.drawable.icon_5, R.drawable.icon_7)
     var currentIconIndex = 0
    fun getIcon(): Int {
        currentIconIndex= (currentIconIndex +1) % icons.size
        return icons[currentIconIndex]
    }
}