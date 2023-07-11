package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.playlistmaker2.R
import com.example.playlistmaker2.SearchActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonSearch = findViewById<Button>(R.id.search)
        val buttonMediateca = findViewById<Button>(R.id.mediateca)
        val buttonSetings = findViewById<Button>(R.id.setings)
        buttonSearch.setOnClickListener {
            val displayIntent = Intent(this, SearchActivity::class.java)
            startActivity(displayIntent)
        }
        buttonMediateca.setOnClickListener {
            val displayIntent = Intent(this, MediatecaActivity::class.java)
            startActivity(displayIntent)
        }
        buttonSetings.setOnClickListener {
            val displayIntent = Intent(this, SettingsActivity::class.java)
            startActivity(displayIntent)
        }

        /*val searchButtonClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@MainActivity, "Нажали на кнопку поиск!", Toast.LENGTH_SHORT).show()
            }
        }
        buttonSearch.setOnClickListener(searchButtonClickListener)
        val mediaButtonClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@MainActivity, "Нажали на кнопку медиатека!", Toast.LENGTH_SHORT).show()
            }
        }
        buttonMediateca.setOnClickListener(mediaButtonClickListener)*/

    }

}