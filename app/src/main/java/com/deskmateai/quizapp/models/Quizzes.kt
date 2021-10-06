package com.deskmateai.quizapp.models

data class Quizzes(
    var id:String = "",
    var title:String = "",
    var questions:MutableMap<String,Questions> = mutableMapOf()
)