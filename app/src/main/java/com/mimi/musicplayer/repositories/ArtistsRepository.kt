package com.mimi.musicplayer.repositories

import androidx.room.withTransaction
import com.mimi.musicplayer.RefreshTime
import com.mimi.musicplayer.data.AppDatabase
import com.mimi.musicplayer.data.dao.ArtistsDao
import com.mimi.musicplayer.data.models.DetailedArtist
import com.mimi.musicplayer.networking.ApiService
import com.mimi.musicplayer.util.networkBoundResource
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ArtistsRepository @Inject constructor(
    private val apiService: ApiService,
    private val db: AppDatabase
) {

    fun getTopArtists() = networkBoundResource(
        query = {
            db.artistsDao.getArtists()
        },
        fetch = {
            apiService.getPopularTracks()
        },
        saveFetchResult = { response ->
            db.withTransaction {
                db.artistsDao.clearArtists()
            }
            val artistIds = response.map { it.user }
            artistIds.map { artistID ->
                CoroutineScope(Dispatchers.IO).async {
                    artistID?.let { getArtistDetails(it) }
                }

            }.awaitAll()
        },
        shouldFetch = { needsRefresh() }

    )

    private suspend fun getArtistDetails(id: String) = withContext(Dispatchers.IO) {
        val response = apiService.getArtist(id).apply { timestamp = Date().time }
        db.artistsDao.insertArtists(response)

    }


    private suspend fun needsRefresh(): Boolean {
        db.artistsDao.getOldestTimestamp()?.let {
            return Date().time - it > RefreshTime.REFRESH_TIMER
        }
        return true
    }

}