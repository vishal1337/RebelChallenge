package com.rebel.challenge.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.rebel.challenge.ui.home.HomeActivity

class SplashActivity : AppCompatActivity() {

    private val SHORT_DELAY = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToHome()
    }

    private fun navigateToHome() {

        Handler().postDelayed({
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, SHORT_DELAY)

    }

}