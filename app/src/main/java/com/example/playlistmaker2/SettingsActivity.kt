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
            Intent(Intent.ACTION_SEND).apply {
                type="plain/text"
                putExtra(Intent.EXTRA_TEXT, getString(R.string.practicumLink))
                startActivity(this)
            }
        }
        buttonSend.setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.gmail)))
                putExtra(
                    Intent.EXTRA_SUBJECT,
                    getString(R.string.mail_subject)
                )
                putExtra(
                    Intent.EXTRA_TEXT,
                    getString(R.string.mail_text)
                )
                startActivity(this)
            }
        }
        buttonRules.setOnClickListener {
            Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(getString(R.string.practicumOffer))
                startActivity(this)
            }

        }

    }

}