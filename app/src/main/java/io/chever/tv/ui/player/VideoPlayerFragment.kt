package io.chever.tv.ui.player

import android.os.Bundle
import android.view.View
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost
import androidx.leanback.media.PlaybackGlue
import androidx.leanback.media.PlaybackTransportControlGlue
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ext.leanback.LeanbackPlayerAdapter
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import io.chever.tv.R
import io.chever.tv.common.extension.Extensions.showShortToast
import io.chever.tv.ui.common.models.YTVideoTrailer
import timber.log.Timber

/**
 * TODO: document class
 */
class VideoPlayerFragment : VideoSupportFragment() {

    private lateinit var trailer: YTVideoTrailer

    private lateinit var simplePlayer: ExoPlayer
    private lateinit var playerAdapter: LeanbackPlayerAdapter
    private lateinit var playerGlue: PlaybackTransportControlGlue<LeanbackPlayerAdapter>
    private lateinit var glueHost: VideoSupportFragmentGlueHost


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {

            initArguments()

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
            requireContext().showShortToast(R.string.app_unknown_error_three)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {

            setupVideoPlayer()

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
            requireContext().showShortToast(R.string.app_unknown_error_one)
        }
    }


    private fun initArguments() {

        requireActivity().intent?.let {

            trailer = it.getSerializableExtra(
                PlayerActivity.PARAM_VIDEO_ITEM
            ) as YTVideoTrailer
        }
    }

    private fun setupVideoPlayer() {

        requireActivity().window?.setBackgroundDrawableResource(
            android.R.color.background_dark
        )

        glueHost = VideoSupportFragmentGlueHost(this)

        simplePlayer = ExoPlayer.Builder(requireContext()).build()

        playerAdapter =
            LeanbackPlayerAdapter(requireActivity(), simplePlayer, PLAYER_ADAPTER_UPDATE_DELAY)

        playerGlue = PlaybackTransportControlGlue(requireActivity(), playerAdapter)
        playerGlue.apply {

            host = glueHost

            title = trailer.title
            subtitle = trailer.description

            isSeekEnabled = true

            playWhenPrepared()

            addPlayerCallback(object : PlaybackGlue.PlayerCallback() {

                override fun onPlayCompleted(glue: PlaybackGlue?) {
                    super.onPlayCompleted(glue)

                    requireActivity().finish()
                }
            })
        }


        val videoSource = createMediaSourceItem(trailer.videoSourceUrl)
        val audioSource = createMediaSourceItem(trailer.audioSourceUrl)

        val mergingSource = MergingMediaSource(videoSource, audioSource)

        simplePlayer.addMediaSource(mergingSource)

        simplePlayer.prepare()
    }


    private fun createMediaSourceItem(url: String): ProgressiveMediaSource {

        val dsFactory = DefaultHttpDataSource.Factory()

        return ProgressiveMediaSource.Factory(dsFactory)
            .createMediaSource(MediaItem.fromUri(url))
    }


    override fun onPause() {

        try {

            playerGlue.pause()

        } catch (ex: Exception) {

            ex.printStackTrace()
        }

        super.onPause()
    }

    override fun onDestroy() {

        try {

            playerGlue.pause()
            simplePlayer.release()

        } catch (ex: Exception) {

            ex.printStackTrace()
        }

        super.onDestroy()
    }


    companion object {

        private const val PLAYER_ADAPTER_UPDATE_DELAY = 16
    }
}