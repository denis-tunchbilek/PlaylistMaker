package com.example.testapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.example.playlistmaker2.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val buttonShare = findViewById<ImageButton>(R.id.s)
        val buttonBack = findViewById<ImageButton>(R.id.back)
        buttonBack.setOnClickListener {
            finish()
        }
        buttonShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.setType("plain/text")
            shareIntent.putExtra(Intent.EXTRA_TEXT, "https://practicum.yandex.ru/android-developer/")
            startActivity(Intent.createChooser(shareIntent,"Поделиться"))
        }


    }
}