package com.mimi.musicplayer.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mimi.musicplayer.data.dao.ArtistsDao
import com.mimi.musicplayer.data.models.DetailedArtist

@Database(
    entities = [DetailedArtist::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract val artistsDao: ArtistsDao


}