package com.example.playlistmaker2.domain.impl

import com.example.playlistmaker2.domain.api.SongsInteractor
import com.example.playlistmaker2.domain.api.SongsRepository
import java.util.concurrent.Executors

class SongsInteractorImpl(private val repository: SongsRepository) : SongsInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchSongs(expression: String, consumer: SongsInteractor.SongsConsumer) {
        executor.execute {
            consumer.consume(repository.searchSongs(expression))
        }
    }
}