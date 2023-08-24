package com.example.playlistmaker2

import android.content.SharedPreferences
import com.google.gson.Gson

class SongHistoryPreference(private val sharedPreferences: SharedPreferences) {
    fun add(searchHistoryTracks: List<Song>) {
        sharedPreferences.edit().putString(SEARCH_HISTORY_KEY, createJsonFromTrackList(searchHistoryTracks)).apply()
    }

    fun get(): Array<Song> {
        return createTracksFromJson(sharedPreferences.getString(SEARCH_HISTORY_KEY, JSON_EMPTY_LIST) ?: JSON_EMPTY_LIST)
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    private fun createJsonFromTrackList(tracks: List<Song>): String {
        return Gson().toJson(tracks)
    }

    private fun createTracksFromJson(json: String): Array<Song> {
        return Gson().fromJson(json, Array<Song>::class.java)
    }

    companion object {
        private const val SEARCH_HISTORY_KEY = "SEARCH_HISTORY_KEY"
        private const val JSON_EMPTY_LIST = "[]"
    }
}