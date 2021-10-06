package com.deskmateai.quizapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deskmateai.quizapp.R
import com.deskmateai.quizapp.adapters.OptionAdapter
import com.deskmateai.quizapp.models.Questions
import com.deskmateai.quizapp.models.Quizzes
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class QuestionActivity : AppCompatActivity() {
    lateinit var description:TextView
    lateinit var optionRecView:RecyclerView
// setting up variable for calling questions one by one
    var quizzes:MutableList<Quizzes>? = null
    var questions:MutableMap<String,Questions>? = null
    var index = 1
    lateinit var previousBtn:Button
    lateinit var nextBtn:Button
    lateinit var submitBtn:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        description = findViewById(R.id.description)
        optionRecView = findViewById(R.id.option_recyclerView)

        previousBtn = findViewById(R.id.btnPrevious)
        nextBtn = findViewById(R.id.btnNext)
        submitBtn = findViewById(R.id.btnSubmit)

        setupFirestore()
        setupEventListener()
    }

    private fun setupEventListener() {
        previousBtn.setOnClickListener {
            index--
            bindViews()
        }

        nextBtn.setOnClickListener {
            index++
            bindViews()
        }

        submitBtn.setOnClickListener {
            Log.d("Final Result",questions.toString())
            val intent = Intent(this,ResultActivity::class.java)
            val gson = Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZ",gson)
            startActivity(intent)
            finish()
        }
    }

    private fun setupFirestore() {
        val fireStore = FirebaseFirestore.getInstance()
        val date = intent.getStringExtra("DATE")
        if (date!=null){
            fireStore.collection("Quizzes").whereEqualTo("title",date)
                .get()
                .addOnSuccessListener {
                    if (it !=null && !it.isEmpty){
                        quizzes = it.toObjects(Quizzes::class.java)
                        questions = quizzes!![0].questions
                        bindViews()
                    }

                }
        }

    }

    private fun bindViews() {
        previousBtn.visibility = View.GONE
        nextBtn.visibility = View.GONE
        submitBtn.visibility = View.GONE

        if (index == 1){  //1st ques
            nextBtn.visibility = View.VISIBLE
        }else if (index== questions!!.size){  //last ques
            submitBtn.visibility = View.VISIBLE
            previousBtn.visibility = View.VISIBLE
        }else{               //middle ques
            previousBtn.visibility = View.VISIBLE
            nextBtn.visibility = View.VISIBLE
        }

        //getting question through firestore with our key name question$index
        val question = questions!!.get("question$index")

        question?.let {
            description.text = it.description
            val optionAdapter = OptionAdapter(this,it)
            optionRecView.layoutManager = LinearLayoutManager(this)
            optionRecView.adapter = optionAdapter

        }


    }

}