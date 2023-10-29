package com.example.playlistmaker2.data.sharedPreferences

import android.content.SharedPreferences
import com.example.playlistmaker2.data.dto.SongDto
import com.example.playlistmaker2.domain.models.Song
import com.google.gson.Gson

class SongHistoryPreference(private val sharedPreferences: SharedPreferences) {
    fun add(searchHistoryTracks: List<SongDto>) {
        sharedPreferences.edit().putString(SEARCH_HISTORY_KEY, createJsonFromTrackList(searchHistoryTracks)).apply()
    }

    fun get(): Array<SongDto> {
        return createTracksFromJson(sharedPreferences.getString(SEARCH_HISTORY_KEY, JSON_EMPTY_LIST) ?: JSON_EMPTY_LIST)
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    private fun createJsonFromTrackList(tracks: List<SongDto>): String {
        return Gson().toJson(tracks)
    }

    private fun createTracksFromJson(json: String): Array<SongDto> {
        return Gson().fromJson(json, Array<SongDto>::class.java)
    }

    companion object {
        private const val SEARCH_HISTORY_KEY = "SEARCH_HISTORY_KEY"
        private const val JSON_EMPTY_LIST = "[]"
    }
}