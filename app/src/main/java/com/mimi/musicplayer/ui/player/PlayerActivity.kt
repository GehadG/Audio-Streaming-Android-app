package com.mimi.musicplayer.ui.player


import android.app.PendingIntent
import android.content.Intent
import android.media.session.PlaybackState
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mimi.musicplayer.R
import com.mimi.musicplayer.TRACKS_FLAG
import com.mimi.musicplayer.data.models.TracksResponse.TracksResponseItem
import com.mimi.musicplayer.databinding.ActivityPlayerBinding
import com.mimi.musicplayer.ui.artists.ArtistListActivity
import dagger.hilt.android.AndroidEntryPoint
import dm.audiostreamer.AudioStreamingManager
import dm.audiostreamer.CurrentSessionCallback
import dm.audiostreamer.MediaMetaData


@AndroidEntryPoint
class PlayerActivity : AppCompatActivity(), CurrentSessionCallback {
    private lateinit var streamingManager: AudioStreamingManager
    private var binding: ActivityPlayerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val track: TracksResponseItem? = intent.getParcelableExtra(TRACKS_FLAG)
        streamingManager = AudioStreamingManager.getInstance(this)
        streamingManager.setShowPlayerNotification(true)
        streamingManager.setPendingIntentAct(getNotificationPendingIntent())
        val mediaInfo = MediaMetaData()
        mediaInfo.apply {
            mediaId = track?.permalink
            mediaUrl = track?.streamUrl
            mediaTitle = track?.title
            mediaDuration = track?.duration
        }
        streamingManager.onPlay(mediaInfo)


        binding?.apply {
            trackTitle.text = track?.title
            genre.text = track?.genre
            progressBar.valueTo = Integer.parseInt(track?.duration).toFloat()
            progressBar.valueFrom = 0f
            progressBar.value = streamingManager.lastSeekPosition().toFloat()
            progressBar.setLabelFormatter { value: Float ->
                formatTime(value.toLong())
            }

            playBtn.setOnClickListener { togglePlay() }
            duration.text =
                "${formatTime(progressBar.value.toLong())} / ${formatTime(progressBar.valueTo.toLong())}"
            Glide.with(this@PlayerActivity).load(track?.artworkUrl).into(trackImage)
        }
    }

    private fun togglePlay() {
        if (streamingManager.isPlaying) {
            streamingManager.handlePauseRequest()
            binding?.playBtn?.setImageResource(R.drawable.ic_play)
        } else {
            streamingManager.handlePlayRequest()
            binding?.playBtn?.setImageResource(R.drawable.ic_pause)
        }
    }

    override fun onStart() {
        super.onStart()
        streamingManager.subscribesCallBack(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onStop() {
        super.onStop()
        streamingManager.unSubscribeCallBack()
    }

    override fun updatePlaybackState(state: Int) {
        when (state) {
            PlaybackState.STATE_PLAYING -> binding?.playBtn?.setImageResource(R.drawable.ic_pause)
            PlaybackState.STATE_PAUSED -> binding?.playBtn?.setImageResource(R.drawable.ic_play)
            PlaybackState.STATE_STOPPED -> finish()
        }

    }

    override fun playSongComplete() {

    }

    override fun currentSeekBarPosition(progress: Int) {
        binding?.apply {
            progressBar.value = (progress / 1000).toFloat()
            duration.text =
                "${formatTime(progressBar.value.toLong())} / ${formatTime(progressBar.valueTo.toLong())}"
        }


    }

    override fun playCurrent(indexP: Int, currentAudio: MediaMetaData?) {

    }

    override fun playNext(indexP: Int, currentAudio: MediaMetaData?) {

    }

    override fun playPrevious(indexP: Int, currentAudio: MediaMetaData?) {

    }

    private fun getNotificationPendingIntent(): PendingIntent? {
        val intent = Intent(this, ArtistListActivity::class.java)
        intent.action = "openplayer"
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        return PendingIntent.getActivity(this, 0, intent, 0)
    }

    private fun formatTime(secs: Long): String {
        return String.format("%02d:%02d:%02d", secs / 3600, secs % 3600 / 60, secs % 60)
    }

}