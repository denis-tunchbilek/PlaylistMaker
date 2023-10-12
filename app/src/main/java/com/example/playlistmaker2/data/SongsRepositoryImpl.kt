package com.example.playlistmaker2.data

import com.example.playlistmaker2.data.dto.SongsSearchRequest
import com.example.playlistmaker2.data.dto.SongsSearchResponse
import com.example.playlistmaker2.domain.api.SongsRepository
import com.example.playlistmaker2.domain.models.Song

class SongsRepositoryImpl(private val networkClient: NetworkClient) : SongsRepository {

    override fun searchSongs(expression: String): List<Song> {
        val response = networkClient.doRequest(SongsSearchRequest(expression))
        if (response.resultCode == 200) {
            return (response as SongsSearchResponse).results.map {
                Song(
                    it.trackName,
                    it.artistName,
                    it.getFormattedDuration(),
                    it.getCoverArtwork(),
                    it.collectionName,
                    it.getYear(),
                    it.primaryGenreName,
                    it.country,
                    it.previewUrl) }
        } else {
            return emptyList()
        }
    }
}