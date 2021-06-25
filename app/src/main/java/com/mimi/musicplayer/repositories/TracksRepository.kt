package com.mimi.musicplayer.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mimi.musicplayer.data.TracksPagingSource
import com.mimi.musicplayer.data.models.TracksResponse
import com.mimi.musicplayer.networking.ApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class TracksRepository @Inject constructor(
    private val apiService: ApiService
) {


    fun getTracksByArtist(id: String): Flow<PagingData<TracksResponse.TracksResponseItem>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { TracksPagingSource(apiService, id) }
        ).flow
    }

}