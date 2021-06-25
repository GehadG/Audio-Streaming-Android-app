package com.mimi.musicplayer.ui.artists

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.mimi.musicplayer.ARTIST_FLAG
import com.mimi.musicplayer.data.models.DetailedArtist
import com.mimi.musicplayer.databinding.ActivityArtistListBinding
import com.mimi.musicplayer.ui.tracks.TracksListActivity
import com.mimi.musicplayer.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtistListActivity : AppCompatActivity() {
    private val viewModel: ArtistsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityArtistListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val artistListAdapter = ArtistListAdapter(onItemClicked = {
            onItemClick(it)
        })

        binding.apply {
            recyclerView.apply {
                adapter = artistListAdapter
                layoutManager = LinearLayoutManager(this@ArtistListActivity)
            }

            viewModel.getArtists().observe(this@ArtistListActivity) { result ->
                textTop.isVisible = !result.data.isNullOrEmpty()
                artistListAdapter.submitList(result.data?.sortedBy { it.username })
                progressLogo.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                textErr.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
                textErr.text = result.error?.localizedMessage
            }
        }
    }


    private fun onItemClick(model: DetailedArtist) {
        val intent = Intent(this, TracksListActivity::class.java)
        intent.putExtra(ARTIST_FLAG, model)
        startActivity(intent)
    }
}