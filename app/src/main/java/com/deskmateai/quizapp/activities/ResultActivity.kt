package com.deskmateai.quizapp.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import com.deskmateai.quizapp.R
import com.deskmateai.quizapp.models.Quizzes
import com.google.gson.Gson
import java.lang.StringBuilder

class ResultActivity : AppCompatActivity() {
    lateinit var scoreTextView:TextView
    lateinit var txtAnswer:TextView
    lateinit var quiz:Quizzes
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        scoreTextView = findViewById(R.id.score_text)
        txtAnswer = findViewById(R.id.txtAnswer)

        setupViews()
    }

    private fun setupViews() {
        val quizData = intent.getStringExtra("QUIZ")
        quiz = Gson().fromJson<Quizzes>(quizData,Quizzes::class.java)
        calculateScore()
        setAnswerViews()
    }

    private fun setAnswerViews() {
        val builder = StringBuilder("")
        for (entry in quiz.questions.entries){
            val questions = entry.value
            builder.append("<font color'#18206F'><b>Question: ${questions.description}</b></font><br/><br/>")
            builder.append("<font color='#009688'>Answer: ${questions.answer}</font><br/><br/>")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            txtAnswer.text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT)
        }else{
            txtAnswer.text = Html.fromHtml(builder.toString())
        }
    }

    private fun calculateScore() {
        var score = 0
        for (entry in quiz.questions.entries){
            val questions = entry.value
            if (questions.answer == questions.userAnswer){
                score +=2
            }
        }
        scoreTextView.text = "Your score is : $score"
    }
}