package com.deskmateai.quizapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.airbnb.lottie.LottieAnimationView
import com.deskmateai.quizapp.R

class SplashScreen : AppCompatActivity() {
    private lateinit var lottie: LottieAnimationView
    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        lottie = findViewById(R.id.lottie_view)

        handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
        },1500)


    }
}