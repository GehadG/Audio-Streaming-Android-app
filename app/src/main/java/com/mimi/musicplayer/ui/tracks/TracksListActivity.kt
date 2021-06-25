package com.mimi.musicplayer.ui.tracks

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.mimi.musicplayer.ARTIST_FLAG
import com.mimi.musicplayer.TRACKS_FLAG
import com.mimi.musicplayer.data.models.DetailedArtist
import com.mimi.musicplayer.data.models.TracksResponse.TracksResponseItem
import com.mimi.musicplayer.databinding.ActivityTracksListBinding
import com.mimi.musicplayer.ui.player.PlayerActivity
import com.mimi.musicplayer.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TracksListActivity : AppCompatActivity() {
    private val viewModel: TracksViewModel by viewModels()
    private var searchJob: Job? = null

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTracksListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val artistData: DetailedArtist? = intent.getParcelableExtra<DetailedArtist>(ARTIST_FLAG)
        val tracksListAdapter = TrackListAdapter(onItemClicked = {
            onItemClick(it)
        })
        tracksListAdapter.addLoadStateListener { loadState ->
            binding.apply {
                textTop.isVisible = tracksListAdapter.itemCount != 0
                progressLogo.isVisible = loadState.mediator?.refresh is LoadState.Loading
            }
        }
        binding.apply {
            recyclerView.apply {
                adapter = tracksListAdapter
                layoutManager = LinearLayoutManager(this@TracksListActivity)
            }
            textTop.text = artistData?.username
        }
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            artistData?.permalink?.let { it ->
                viewModel.getTracksByArtist(it).collectLatest {
                    tracksListAdapter.submitData(this@TracksListActivity.lifecycle,it)
                }
            }


        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun onItemClick(model: TracksResponseItem) {
        val intent = Intent(this, PlayerActivity::class.java)
        intent.putExtra(TRACKS_FLAG, model)
        startActivity(intent)
    }
}