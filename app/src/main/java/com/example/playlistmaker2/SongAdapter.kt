package com.example.playlistmaker2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SongAdapter() : RecyclerView.Adapter<SongViewHolder>() {
    var songs = ArrayList<Song>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(parent)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(songs[position])
    }

    override fun getItemCount(): Int {
        return songs.size
    }
}