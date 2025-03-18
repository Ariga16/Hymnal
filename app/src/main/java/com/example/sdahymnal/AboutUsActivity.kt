package com.example.sdahymnal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AboutUsActivity : AppCompatActivity() {

    private lateinit var callBtn: ImageView
    private lateinit var imgback: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about_us)
        callBtn = findViewById(R.id.callBtn)
        callBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+254796439903")
            startActivity(intent)
        }

        imgback = findViewById(R.id.imgback)
        imgback.setOnClickListener {
            finish()
        }

    }
}