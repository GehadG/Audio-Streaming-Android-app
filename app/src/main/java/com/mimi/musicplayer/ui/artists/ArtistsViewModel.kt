package com.mimi.musicplayer.ui.artists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mimi.musicplayer.repositories.ArtistsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArtistsViewModel @Inject constructor(private val artistsRepository: ArtistsRepository) :
    ViewModel() {

    fun getArtists() = artistsRepository.getTopArtists().asLiveData()
}