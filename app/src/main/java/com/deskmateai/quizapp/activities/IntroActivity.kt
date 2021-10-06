package com.deskmateai.quizapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.deskmateai.quizapp.R
import com.google.firebase.auth.FirebaseAuth

class IntroActivity : AppCompatActivity() {
    private lateinit var btn: Button
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        btn = findViewById(R.id.get_startedButton)
        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser!=null){
            redirectUser("MAIN")
        }
        btn.setOnClickListener {
            redirectUser("LOGIN")
        }

    }

    //creating a redirection function which accepts a String as a parameter
    // and check whether to where he should be redirect
    private fun redirectUser(name:String){
        val intent = when(name){
            "LOGIN" -> Intent(this, LoginActivity::class.java)
            "MAIN" -> Intent(this, MainActivity::class.java)
            else -> throw Exception("No path exists")
        }
        startActivity(intent)
        finish()
    }
}