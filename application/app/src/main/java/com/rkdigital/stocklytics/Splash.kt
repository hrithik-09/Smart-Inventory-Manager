package com.rkdigital.stocklytics

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.os.Handler
import android.os.Looper
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.rkdigital.stocklytics.ViewModel.LoginViewModel
import com.rkdigital.stocklytics.ViewModel.LoginViewModelFactory
import com.rkdigital.stocklytics.repository.AuthRepository

import com.rkdigital.stocklytics.serviceApi.RetrofitInstance
import com.rkdigital.stocklytics.storage.SharedPreferenceHelper
import com.rkdigital.stocklytics.utils.SharedPrefKeys

class Splash : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var prefs: SharedPreferenceHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val apiService = RetrofitInstance.apiService
        val authRepository = AuthRepository(apiService)
        val factory = LoginViewModelFactory(authRepository)

        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        prefs = SharedPreferenceHelper.getInstance(this)
        val logo = findViewById<ImageView>(R.id.logoImage)
        val glow = findViewById<ImageView>(R.id.glowEffect)
        val glowAnimation = AnimationUtils.loadAnimation(this@Splash, R.anim.glow_pulse).also {
            glow.startAnimation(it)
            glow.animate().alpha(0.3f).duration = 1000
        }
        logo.startAnimation(glowAnimation)

        val token = prefs.getUserPrefs().getString(SharedPrefKeys.TOKEN, null)

        if (token.isNullOrEmpty()) {
            goToLogin()
        } else {
            viewModel.validateToken(token)
        }
        viewModel.tokenValidationResult.observe(this) { result ->
            result.onSuccess {
                goToMain()
            }.onFailure {
                prefs.putBoolean(prefs.getUserPrefs(), SharedPrefKeys.IS_LOGGED_IN, false)
                goToLogin()
            }
        }
    }

    private fun goToMain() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1000)
    }

    private fun goToLogin() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, Login::class.java))
            finish()
        }, 1000)
    }
}