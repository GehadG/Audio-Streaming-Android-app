package com.mimi.musicplayer.ui.tracks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mimi.musicplayer.data.models.TracksResponse.TracksResponseItem
import com.mimi.musicplayer.repositories.TracksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel

class TracksViewModel @ExperimentalPagingApi
@Inject constructor(private val tracksRepository: TracksRepository) :
    ViewModel() {

    private var currentArtistId: String? = null

    private var currentTracks: Flow<PagingData<TracksResponseItem>>? = null

    @ExperimentalPagingApi
    fun getTracksByArtist(id: String): Flow<PagingData<TracksResponseItem>> {
        println("xxxxxxxx${currentTracks}u")
        val lastResult = currentTracks
        if (id == currentArtistId && lastResult != null) {
            return lastResult
        }
        currentArtistId = id
        val newResult: Flow<PagingData<TracksResponseItem>> =
            tracksRepository.getTracksByArtist(id).cachedIn(viewModelScope)
        currentTracks = newResult
        return newResult
    }


}


