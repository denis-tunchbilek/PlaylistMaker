package com.example.playlistmaker2.data.network

import com.example.playlistmaker2.data.NetworkClient
import com.example.playlistmaker2.data.dto.Response
import com.example.playlistmaker2.data.dto.SongsSearchRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitNetworkClient : NetworkClient {

    private val iTunesBaseUrl = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(iTunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val iTunesApi= retrofit.create(iTunesApi::class.java)

    override fun doRequest(dto: Any): Response {
        if (dto is SongsSearchRequest) {
            val resp = iTunesApi.search(dto.expression).execute()

            val body = resp.body() ?: Response()

            return body.apply { resultCode = resp.code() }
        } else {
            return Response().apply { resultCode = 400 }
        }
    }
}