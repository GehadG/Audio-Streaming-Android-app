package com.mimi.musicplayer.networking

import com.mimi.musicplayer.data.models.DetailedArtist
import com.mimi.musicplayer.data.models.FeedResponse
import com.mimi.musicplayer.data.models.TracksResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/feed?page=1&count=20&type=popular")
    suspend fun getPopularTracks(): FeedResponse

    @GET("/{permlink}?count=20&type=tracks")
    suspend fun getArtistTracks(
        @Path("permlink") stub: String,
        @Query("page") page: Int
    ): TracksResponse

    @GET("/{permlink}")
    suspend fun getArtist(
        @Path("permlink") stub: String
    ): DetailedArtist
}
