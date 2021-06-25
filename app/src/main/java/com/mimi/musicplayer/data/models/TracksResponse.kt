package com.mimi.musicplayer.data.models


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.mimi.musicplayer.data.models.TracksResponse.TracksResponseItem
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class TracksResponse : ArrayList<TracksResponseItem>(), Parcelable {
    @Parcelize
    @Entity(tableName = "tracks")
    data class TracksResponseItem(
        @SerializedName("artwork_url") val artworkUrl: String?,
        @SerializedName("background_url") val backgroundUrl: String?,
        @SerializedName("comment_count") val commentCount: String?,
        @SerializedName("created_at") val createdAt: String?,
        @SerializedName("description") val description: String?,
        @SerializedName("download_count") val downloadCount: String?,
        @SerializedName("download_url") val downloadUrl: String?,
        @SerializedName("downloadable") val downloadable: String?,
        @SerializedName("duration") val duration: String?,
        @SerializedName("favorited") val favorited: Boolean,
        @SerializedName("favoritings_count") val favoritingsCount: String?,
        @SerializedName("genre") val genre: String?,
        @SerializedName("genre_slush") val genreSlush: String?,
        @PrimaryKey(autoGenerate = false)
        @SerializedName("permalink") val permalink: String,
        @SerializedName("permalink_url") val permalinkUrl: String?,
        @SerializedName("playback_count") val playbackCount: String?,
        @SerializedName("stream_url") val streamUrl: String?,
        @SerializedName("title") val title: String?,
        @SerializedName("uri") val uri: String?,
        @JsonAdapter(UsernameDeserializer::class)
        @SerializedName("user") val user: String?,
        @SerializedName("user_id") val userId: String?,
        @SerializedName("waveform_data") val waveformData: String?,
        @SerializedName("waveform_url") val waveformUrl: String?
    ) : Parcelable {
        var timestamp: Long = 0


    }
}