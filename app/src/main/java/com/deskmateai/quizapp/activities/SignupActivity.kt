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

class SignupActivity : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var passwd: EditText
    private lateinit var confirmPaswd: EditText
    private lateinit var btnSignUp: Button
    private lateinit var loginBtnText: TextView
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        email = findViewById(R.id.et_Signup_EmailAddress)
        passwd = findViewById(R.id.etSignup_Password)
        confirmPaswd = findViewById(R.id.etConfirm_Password)
        firebaseAuth = FirebaseAuth.getInstance()

        btnSignUp = findViewById(R.id.button_signup)
        btnSignUp.setOnClickListener {
            signUpUser()
        }

        //setting on click;listener on text view that leads us to login activity
        loginBtnText = findViewById(R.id.signIn_btn_textview)
        loginBtnText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

//     creating a signup function
    private fun signUpUser(){
        val emailAddress = email.text.toString()
        val password = passwd.text.toString()
        val confirmPassword = confirmPaswd.text.toString()

        //creating some validation condition for user input data
        if (emailAddress.isBlank() || password.isBlank() || confirmPassword.isBlank()){
            Toast.makeText(this,"E-mail and passwords can't be blank",Toast.LENGTH_SHORT).show()
            return
        }
        if (password!=confirmPassword){
            Toast.makeText(this,"Password and Confirm Password doesn't match",Toast.LENGTH_SHORT).show()
            return
        }

        // this function will create a user on the basis of emailAddress and password
        firebaseAuth.createUserWithEmailAndPassword(emailAddress,password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this,"Sign Up successful",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this,"Error in creating User",Toast.LENGTH_SHORT).show()
                }
            }


    }
}