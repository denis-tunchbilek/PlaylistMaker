package com.example.playlistmaker2

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SongAdapter(private val onTrackClickListener: OnTrackClickListener) : RecyclerView.Adapter<SongViewHolder>() {
    var songs = ArrayList<Song>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(parent, onTrackClickListener)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.bind(song)

    }

    override fun getItemCount(): Int {
        return songs.size
    }
}