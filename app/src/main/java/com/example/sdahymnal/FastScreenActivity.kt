package com.example.sdahymnal

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FastScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fast_screen)

        val tv: TextView = findViewById(R.id.tvNyimbo)
        val iv: ImageView = findViewById(R.id.ivLogo)

        val myanim = AnimationUtils.loadAnimation(this, R.anim.mytransition)
        tv.startAnimation(myanim)
        iv.startAnimation(myanim)

        val intent = Intent(this, SecondActivity::class.java)

        Thread {
            try {
                Thread.sleep(5000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                startActivity(intent)
                finish()
            }
        }.start()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}