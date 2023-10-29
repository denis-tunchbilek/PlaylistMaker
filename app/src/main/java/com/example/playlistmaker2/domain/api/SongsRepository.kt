package com.example.playlistmaker2.domain.api

import com.example.playlistmaker2.domain.models.Song

interface SongsRepository {
    fun searchSongs(expression: String): List<Song>
}