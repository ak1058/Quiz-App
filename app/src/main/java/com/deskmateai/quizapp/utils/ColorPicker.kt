package com.deskmateai.quizapp.utils

object ColorPicker {
    val colors = arrayOf("#f6cd61", "#E91E63", "#fe8a71", "#3da4ab", "#0e9aa7", "#4a4e4d"  )

    var currentColorIndex = 0
    fun getColor():String{
        currentColorIndex = (currentColorIndex+1) % colors.size
        return colors[currentColorIndex]
    }
}