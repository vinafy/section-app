package com.bangkit.section.ui.auth

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.bangkit.section.R
import com.bangkit.section.data.preference.UserPreference
import com.bangkit.section.ui.MainActivity
import com.bangkit.section.ui.auth.login.LoginActivity

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        val sharedPref = UserPreference.initPref(this, "onSignIn")
        val token = sharedPref.getString("apiKey", null)
        Log.e(ContentValues.TAG, "token: $token")
        if (token != null) {
            val intent = Intent(this, MainActivity::class.java)
            navigate(intent)
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            navigate(intent)
        }
    }
    private fun navigate(intent: Intent) {
        val SPLASH_SCREEN_TIMER: Long = 2000
        val splashTimer: Long = SPLASH_SCREEN_TIMER
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
        }, splashTimer)
    }
}


