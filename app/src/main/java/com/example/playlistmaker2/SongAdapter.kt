package com.example.playlistmaker2

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SongAdapter(private val onTrackClickListener: (track: Song) -> Unit) : RecyclerView.Adapter<SongViewHolder>() {
    var songs = ArrayList<Song>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(parent)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.bind(song)
        holder.itemView.setOnClickListener {
            onTrackClickListener(song) // Вызов переданной лямбды после клика
        }

    }

    override fun getItemCount(): Int {
        return songs.size
    }
}