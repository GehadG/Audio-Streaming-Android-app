package com.mimi.musicplayer

import java.util.concurrent.TimeUnit

const val BASE_URL = "https://api-v2.hearthis.at"
const val ARTIST_FLAG = "artists"
const val TRACKS_FLAG = "tracks"

class RefreshTime {
    companion object {
        var REFRESH_TIMER = TimeUnit.HOURS.toMillis(1)
    }
}


