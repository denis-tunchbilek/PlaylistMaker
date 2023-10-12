package com.example.playlistmaker2.domain.api

import com.example.playlistmaker2.domain.models.Song

interface SongsInteractor {
    fun searchSongs(expression: String, consumer: SongsConsumer)

    interface SongsConsumer {
        fun consume(foundMovies: List<Song>)
    }
}