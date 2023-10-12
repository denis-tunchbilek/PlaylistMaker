package com.example.playlistmaker2

import com.example.playlistmaker2.data.SongsRepositoryImpl
import com.example.playlistmaker2.data.network.RetrofitNetworkClient
import com.example.playlistmaker2.domain.api.SongsInteractor
import com.example.playlistmaker2.domain.api.SongsRepository
import com.example.playlistmaker2.domain.impl.SongsInteractorImpl

object Creator {
    private fun getSongsRepository(): SongsRepository {
        return SongsRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideSongsInteractor(): SongsInteractor {
        return SongsInteractorImpl(getSongsRepository())
    }
}