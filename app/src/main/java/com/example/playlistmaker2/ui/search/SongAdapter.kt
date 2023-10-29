package com.example.playlistmaker2.ui.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker2.data.dto.SongDto
import com.example.playlistmaker2.domain.models.Song

class SongAdapter(private val onTrackClickListener: (track: SongDto) -> Unit) : RecyclerView.Adapter<SongViewHolder>() {
    var songs = ArrayList<SongDto>()
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