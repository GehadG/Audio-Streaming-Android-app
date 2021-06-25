package com.mimi.musicplayer.data.models


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "artists")
data class DetailedArtist(
    @SerializedName("allow_push") val allowPush: Int,
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("background_url") val backgroundUrl: String?,
    @SerializedName("caption") val caption: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("followers_count") val followersCount: String?,
    @SerializedName("following") val following: Boolean,
    @SerializedName("following_count") val followingCount: Int,
    @SerializedName("geo") val geo: String?,
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id") val id: String,
    @SerializedName("likes_count") val likesCount: String?,
    @SerializedName("720p_url") val pUrl: String?,
    @SerializedName("permalink") val permalink: String?,
    @SerializedName("permalink_url") val permalinkUrl: String?,
    @SerializedName("playlist_count") val playlistCount: String?,
    @SerializedName("premium") val premium: Boolean,
    @SerializedName("thumb_url") val thumbUrl: String?,
    @SerializedName("track_count") val trackCount: String?,
    @SerializedName("uri") val uri: String?,
    @SerializedName("username") val username: String
) : Parcelable {
    var timestamp: Long = 0
}