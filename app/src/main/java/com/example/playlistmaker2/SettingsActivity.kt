package com.example.testapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate.NightMode
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import com.example.playlistmaker2.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val buttonShare = findViewById<ImageButton>(R.id.s)
        val buttonBack = findViewById<ImageButton>(R.id.back)
        val buttonSend = findViewById<ImageButton>(R.id.mail)
        val buttonRules = findViewById<ImageButton>(R.id.rules)



        buttonBack.setOnClickListener {
            finish()
        }
        buttonShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "plain/text"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "https://practicum.yandex.ru/android-developer/")
            startActivity(Intent.createChooser(shareIntent, "Поделиться"))
        }
        buttonSend.setOnClickListener {
            val mailIntent = Intent(Intent.ACTION_SEND)
            mailIntent.data = Uri.parse("mailto:")
            mailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("denistuncbilek92718@gmail.com"))
            mailIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                "Сообщение разработчикам и разработчицам приложения Playlist Maker"
            )
            mailIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Спасибо разработчикам и разработчицам за крутое приложение!"
            )
            startActivity(mailIntent)
        }
        buttonRules.setOnClickListener {
            val rulesIntent = Intent(Intent.ACTION_VIEW)
            rulesIntent.data = Uri.parse("https://yandex.ru/legal/practicum_offer/")
            startActivity(rulesIntent)
        }

    }

}