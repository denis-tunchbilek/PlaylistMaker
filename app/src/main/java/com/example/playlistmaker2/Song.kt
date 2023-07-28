package com.example.playlistmaker2

import com.google.gson.annotations.SerializedName

data class Song(
    val trackName: String,
    val artistName: String,
    @SerializedName("trackTimeMillis")val trackTime: String,
    val artworkUrl100: String
) {
}