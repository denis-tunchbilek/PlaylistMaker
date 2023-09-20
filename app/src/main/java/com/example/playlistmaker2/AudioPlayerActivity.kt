package com.example.playlistmaker2

import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import java.util.Locale

class AudioPlayerActivity : AppCompatActivity() {
    private lateinit var backButtonPlayer: ImageButton
    private lateinit var poster: ImageView
    private lateinit var trackName: TextView
    private lateinit var trackArtist: TextView
    private lateinit var trackTime: TextView
    private lateinit var trackAlbum: TextView
    private lateinit var trackYear: TextView
    private lateinit var trackGenre: TextView
    private lateinit var trackCountry: TextView
    private lateinit var albumGroup: Group




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player)
        val jsonTrack = intent.getStringExtra("track")
        val track = Gson().fromJson(jsonTrack, Song::class.java)
        backButtonPlayer = findViewById(R.id.backToMainFromPlayer)
        poster = findViewById(R.id.poster)
        trackName = findViewById(R.id.track_title)
        trackArtist = findViewById(R.id.track_artist)
        trackTime = findViewById(R.id.time_parameter)
        trackAlbum = findViewById(R.id.album_parameter)
        trackYear = findViewById(R.id.year_parameter)
        trackGenre = findViewById(R.id.genre_parameter)
        trackCountry = findViewById(R.id.country_parameter)
        albumGroup = findViewById(R.id.album_group)
        backButtonPlayer.setOnClickListener {
            finish()
        }

        Glide.with(this)
            .load(track.getCoverArtwork())
            .placeholder(R.drawable.place_holder)
            .fitCenter()
            .into(poster)
        trackName.text = track.trackName
        trackArtist.text = track.artistName
        trackTime.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTime.toLong())
        if (track.collectionName.isNotEmpty()) {
            albumGroup.visibility = View.VISIBLE
            trackAlbum.text = track.collectionName
        } else {
            albumGroup.visibility = View.GONE
        }
        trackYear.text = track.getYear()
        trackGenre.text = track.primaryGenreName
        trackCountry.text = track.country

    }
}