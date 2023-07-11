package com.example.playlistmaker2

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val songName: TextView = itemView.findViewById(R.id.songName)
    private val picture: ImageView = itemView.findViewById(R.id.songPicture)
    private val artistName: TextView = itemView.findViewById(R.id.artistName)
    private val songTime: TextView = itemView.findViewById(R.id.songTime)
    fun bind(item: song) {
        songName.text =
            if (item.trackName.length > 30) "${item.trackName.substring(0, 30)}..."
            else item.trackName
        artistName.text =
            if (item.artistName.length > 40) "${item.artistName.substring(0, 40)}..."
            else item.artistName
        songTime.text = item.trackTime
        Glide.with(itemView)
            .load(item.artworkUrl100)
            .placeholder(R.drawable.place_holder)
            .fitCenter()
            .transform(RoundedCorners(10))
            .into(picture)
    }
}