package com.example.myjikan.View

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myjikan.MainActivity
import com.example.myjikan.R
import com.example.myjikan.Utils.CommonUtils.circularRevealAView
import com.example.myjikan.Utils.CommonUtils.waitForSpecifiedTime
import com.example.myjikan.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialization()
    }

    private fun initialization() {
        circularRevealAView(view = binding.imgSplashLogo) {
            val intent = Intent(this@SplashScreen, MainActivity::class.java)
            waitForSpecifiedTime(1000L / 3) {
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.slide_up_from_bottom, R.anim.slide_out_from_top)
            }
        }
    }

}