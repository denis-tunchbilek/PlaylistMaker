package com.example.playlistmaker2.data

import com.example.playlistmaker2.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response

}