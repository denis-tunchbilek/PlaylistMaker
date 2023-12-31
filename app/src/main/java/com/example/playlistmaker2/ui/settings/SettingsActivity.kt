package com.example.playlistmaker2.ui.settings

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Switch
import com.example.playlistmaker2.presentation.APP_SETTINGS_PREF_KEY
import com.example.playlistmaker2.presentation.App
import com.example.playlistmaker2.presentation.DARK_THEME
import com.example.playlistmaker2.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val buttonShare = findViewById<ImageButton>(R.id.s)
        val buttonBack = findViewById<ImageButton>(R.id.back)
        val buttonSend = findViewById<ImageButton>(R.id.mail)
        val buttonRules = findViewById<ImageButton>(R.id.rules)
        val switchTheme = findViewById<Switch>(R.id.theme)
        val switchPreference = getSharedPreferences(APP_SETTINGS_PREF_KEY, MODE_PRIVATE)
        switchTheme.isChecked = (applicationContext as App).darkTheme


        switchTheme.setOnCheckedChangeListener { switcher, checked ->
            (applicationContext as App).switchTheme(checked)
            switchPreference.edit()
                .putBoolean(DARK_THEME, checked)
                .apply()
        }



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