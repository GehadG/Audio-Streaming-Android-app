package com.mimi.musicplayer.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mimi.musicplayer.data.models.DetailedArtist
import com.mimi.musicplayer.data.models.TracksResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: List<DetailedArtist>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtists(artist: DetailedArtist)

    @Query("SELECT * FROM artists")
    fun getArtists(): Flow<List<DetailedArtist>>

    @Query("DELETE FROM artists")
    suspend fun clearArtists()


    @Query("SELECT timestamp from artists ORDER BY timestamp DESC LIMIT 1")
    suspend fun getOldestTimestamp(): Long?

    @Query("SELECT * FROM artists where permalink = :id limit 1")
    fun getArtist(id: String): Flow<DetailedArtist>

}
