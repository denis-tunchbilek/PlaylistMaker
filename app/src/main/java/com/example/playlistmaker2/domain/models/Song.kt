package com.example.playlistmaker2.domain.models

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Locale

data class Song(
    val trackName: String,
    val artistName: String,
    val collectionName: String,
    val releaseDate: String,
    val primaryGenreName: String,
    val country: String,
    val previewUrl: String,
    @SerializedName("trackTimeMillis")val trackTime: String,
    val artworkUrl100: String
) {

        fun getCoverArtwork() = artworkUrl100.replaceAfterLast('/',"512x512bb.jpg")
        fun getFormattedDuration() = SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTime.toInt())

        fun getYear() = releaseDate.substring(0,4)


}