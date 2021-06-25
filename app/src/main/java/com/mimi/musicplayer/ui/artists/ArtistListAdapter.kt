package com.mimi.musicplayer.ui.artists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mimi.musicplayer.R
import com.mimi.musicplayer.data.models.DetailedArtist
import com.mimi.musicplayer.databinding.ArtistItemBinding


class ArtistListAdapter(val onItemClicked: (DetailedArtist) -> Unit) :
    ListAdapter<DetailedArtist, ArtistListAdapter.ArtistItemViewHolder>(ArtistComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistItemViewHolder {
        val binding =
            ArtistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtistItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
            holder.itemView.setOnClickListener { onItemClicked(currentItem) }
        }
    }

    class ArtistItemViewHolder(private val binding: ArtistItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: DetailedArtist) {
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatarUrl)
                    .error(R.drawable.unavailable)
                    .into(artistImg)
                title.text = user.username
                geo.text = user.geo
                trackCount.text = user.trackCount
            }
        }
    }

    class ArtistComparator : DiffUtil.ItemCallback<DetailedArtist>() {
        override fun areItemsTheSame(oldItem: DetailedArtist, newItem: DetailedArtist) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: DetailedArtist, newItem: DetailedArtist) =
            oldItem == newItem
    }
}
