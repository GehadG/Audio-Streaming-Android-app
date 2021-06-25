package com.mimi.musicplayer.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mimi.musicplayer.data.models.TracksResponse.TracksResponseItem
import com.mimi.musicplayer.networking.ApiService
import retrofit2.HttpException
import java.io.IOException

class TracksPagingSource(
private val service: ApiService,
private val artistId: String
) : PagingSource<Int, TracksResponseItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TracksResponseItem> {
        val position = params.key ?: 1
        return try {
            val response = service.getArtistTracks(artistId,position)
            val nextKey = if (response.isEmpty()) {
                null
            } else {

                position + 1
            }
            LoadResult.Page(
                data = response,
                prevKey = if (position == 1) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    // The refresh key is used for the initial load of the next PagingSource, after invalidation
    override fun getRefreshKey(state: PagingState<Int, TracksResponseItem>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}