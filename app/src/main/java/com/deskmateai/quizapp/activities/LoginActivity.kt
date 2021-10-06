package com.deskmateai.quizapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.deskmateai.quizapp.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var passwd: EditText
    private lateinit var loginBtn: Button
    private lateinit var signupbtnText: TextView
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.etEmailAddress)
        passwd = findViewById(R.id.etPassword)
        firebaseAuth = FirebaseAuth.getInstance()

        loginBtn = findViewById(R.id.button_login)
        loginBtn.setOnClickListener {
            loginUser()
        }

        //setting on click;listener on text view that leads us to signup activity
        signupbtnText = findViewById(R.id.signup_btn_textview)
        signupbtnText.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    //creating a login function
    private fun loginUser(){
        val emailAddress = email.text.toString()
        val password = passwd.text.toString()

        //validating condition
        if(emailAddress.isBlank() || password.isBlank()){
            Toast.makeText(this,"Email or password can't be blank",Toast.LENGTH_SHORT).show()
            return
        }

        //this function will login user
        firebaseAuth.signInWithEmailAndPassword(emailAddress,password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this,"Log in Successful",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this,"Authentication failed",Toast.LENGTH_SHORT).show()
                }
            }
    }
}