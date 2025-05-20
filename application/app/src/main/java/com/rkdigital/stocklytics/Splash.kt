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

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val logo = findViewById<ImageView>(R.id.logoImage)
        val glow = findViewById<ImageView>(R.id.glowEffect)
        val glowAnimation = AnimationUtils.loadAnimation(this@Splash, R.anim.glow_pulse).also {
            glow.startAnimation(it)
            glow.animate().alpha(0.3f).duration = 1000
        }
        logo.startAnimation(glowAnimation)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, Login::class.java))
            finish()
        }, 2000)
    }
}