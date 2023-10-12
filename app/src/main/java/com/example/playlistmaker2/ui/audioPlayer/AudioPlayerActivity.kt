package com.example.playlistmaker2.ui.audioPlayer

import android.icu.text.SimpleDateFormat
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import com.bumptech.glide.Glide
import com.example.playlistmaker2.R
import com.example.playlistmaker2.domain.models.Song
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
    private lateinit var play: ImageButton
    private lateinit var playingTime: TextView
    private var mediaPlayer = MediaPlayer()


    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val DELAY_MILLIS = 300L
    }

    private var playerState = STATE_DEFAULT
    private val handler = Handler(Looper.getMainLooper())
    private val timerRunnable = object : Runnable {
        override fun run() {
            playingTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(mediaPlayer.currentPosition)
            handler.postDelayed(this, DELAY_MILLIS)
        }
    }


    private fun preparePlayer(previewUrl: String) {
        mediaPlayer.setDataSource(previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            play.isEnabled = true
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            play.setImageResource(R.drawable.play_button)
            playerState = STATE_PREPARED
        }
    }
    private fun startPlayer() {
        mediaPlayer.start()
        play.setImageResource(R.drawable.stop_button)
        playerState = STATE_PLAYING
        handler.removeCallbacks(timerRunnable)
        handler.post(timerRunnable)
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        play.setImageResource(R.drawable.play_button)
        playerState = STATE_PAUSED
        handler.removeCallbacks(timerRunnable)
    }
    private fun playbackControl() {
        when(playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }
            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }




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
        play = findViewById(R.id.play)
        playingTime = findViewById(R.id.listened_by_the_time)
        preparePlayer(track.previewUrl)
        backButtonPlayer.setOnClickListener {
            finish()
        }
        play.setOnClickListener {
            playbackControl()
        }

        Glide.with(this)
            .load(track.getCoverArtwork())
            .placeholder(R.drawable.place_holder)
            .fitCenter()
            .into(poster)
        trackName.text = track.trackName
        trackArtist.text = track.artistName
        trackTime.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTime.toLong())
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
    override fun onPause() {
        super.onPause()
        pausePlayer()
    }
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(timerRunnable)
        mediaPlayer.release()
    }
}