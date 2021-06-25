package com.mimi.musicplayer.ui.tracks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.mimi.musicplayer.R
import com.mimi.musicplayer.data.models.TracksResponse.TracksResponseItem
import com.mimi.musicplayer.databinding.TrackItemBinding


class TrackListAdapter(val onItemClicked: (TracksResponseItem) -> Unit) :
    PagingDataAdapter<TracksResponseItem, TrackListAdapter.TrackItemViewHolder>(TracksComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackItemViewHolder {
        val binding =
            TrackItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
            holder.itemView.setOnClickListener { onItemClicked(currentItem) }
        }
    }

    class TrackItemViewHolder(private val binding: TrackItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(track: TracksResponseItem) {
            binding.apply {
                Glide.with(itemView)
                    .load(track.backgroundUrl)
                    .error(R.drawable.unavailable)
                    .circleCrop()
                    .into(artistImg)
                title.text = track.title
                genre.text = track.genre
                duration.text = track.duration?.let { formatTime(it.toLong()) }
            }

        }

        private fun formatTime(secs: Long): String {
            return String.format("%02d:%02d:%02d", secs / 3600, secs % 3600 / 60, secs % 60)
        }
    }

    class TracksComparator : DiffUtil.ItemCallback<TracksResponseItem>() {
        override fun areItemsTheSame(oldItem: TracksResponseItem, newItem: TracksResponseItem) =
            oldItem.permalink == newItem.permalink

        override fun areContentsTheSame(oldItem: TracksResponseItem, newItem: TracksResponseItem) =
            oldItem == newItem
    }



}