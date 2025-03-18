package com.example.sdahymnal

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.MenuItem
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SongActivity : AppCompatActivity() {
    private lateinit var songContent: TextView
    private var scrollPosition: Int = 0 // Variable to store scroll position


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_song)

        // Set up the toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Keep the screen on
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        songContent = findViewById(R.id.tvContentOfSong)

        // Retrieve data from the intent
        val intent = intent
        val title = intent.getStringExtra("title of song")
        val content = intent.getStringExtra("content of song")

        // Set the app bar title as the song title
        supportActionBar?.title = title
        songContent.text = content
        songContent.movementMethod = ScrollingMovementMethod()

        // Restore scroll position
        if (savedInstanceState != null) {
            scrollPosition = savedInstanceState.getInt("scrollPosition")
            songContent.post {
                songContent.scrollTo(0, scrollPosition)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainsong)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save scroll position
        scrollPosition = songContent.scrollY
        outState.putInt("scrollPosition", scrollPosition)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            // Navigate back when the back arrow is pressed
            onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

}