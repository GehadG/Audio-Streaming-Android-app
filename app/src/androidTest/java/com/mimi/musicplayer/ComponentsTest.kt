package com.mimi.musicplayer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.mimi.musicplayer.data.AppDatabase
import com.mimi.musicplayer.data.dao.ArtistsDao
import com.mimi.musicplayer.networking.ApiService
import com.mimi.musicplayer.repositories.ArtistsRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named
import com.google.common.truth.Truth.assertThat as assertThat1

@HiltAndroidTest
@SmallTest
class ComponentsTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Inject
    lateinit var artistsRepository: ArtistsRepository


    @Before
    fun setup() {
        hiltRule.inject()
    }



    @Test
    fun test() = runBlocking{
        val artists = artistsRepository.getTopArtists().first()
        assertThat1(artists.data?.size).isGreaterThan(10)
    }


}