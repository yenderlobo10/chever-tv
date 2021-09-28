package io.chever.tv.ui.player

import android.os.Bundle
import android.view.View
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost
import androidx.leanback.media.PlaybackGlue
import androidx.leanback.media.PlaybackTransportControlGlue
import com.github.se_bastiaan.torrentstream.StreamStatus
import com.github.se_bastiaan.torrentstream.Torrent
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.leanback.LeanbackPlayerAdapter
import com.google.android.exoplayer2.video.VideoSize
import com.orhanobut.logger.Logger
import io.chever.tv.R
import io.chever.tv.common.extension.Extensions.disableKeepScreenOn
import io.chever.tv.common.extension.Extensions.enableKeepScreenOn
import io.chever.tv.common.extension.Extensions.showShortToast
import io.chever.tv.common.extension.FSymbols
import io.chever.tv.common.extension.NumberExtensions.toFormatPercent
import io.chever.tv.common.torrent.service.TorrentService
import io.chever.tv.common.torrent.service.TorrentServiceListener
import io.chever.tv.ui.common.models.PlayVideo

/**
 * TODO: document class
 */
class TorrentVideoPlayerFragment(
    private val playVideo: PlayVideo,
    private val torrentService: TorrentService
) : VideoSupportFragment(),
    TorrentServiceListener,
    Player.Listener {

    private lateinit var simplePlayer: SimpleExoPlayer
    private lateinit var playerAdapter: LeanbackPlayerAdapter
    private lateinit var playerGlue: PlaybackTransportControlGlue<LeanbackPlayerAdapter>
    private lateinit var glueHost: VideoSupportFragmentGlueHost


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {

            setupVideoPlayer()

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
            requireContext().showShortToast(R.string.app_unknown_error_one)
        }
    }

    // TODO: simplify this method
    private fun setupVideoPlayer() {

        requireActivity().window?.setBackgroundDrawableResource(
            android.R.color.background_dark
        )

        torrentService.addListener(this)

        glueHost = VideoSupportFragmentGlueHost(this)

        simplePlayer = SimpleExoPlayer.Builder(requireContext()).build()

        playerAdapter =
            LeanbackPlayerAdapter(
                requireActivity(), simplePlayer,
                PLAYER_ADAPTER_UPDATE_DELAY
            )

        playerGlue = PlaybackTransportControlGlue(requireActivity(), playerAdapter)
        playerGlue.apply {

            host = glueHost

            title = playVideo.title
            subtitle = playVideo.description

            isSeekEnabled = true

            playWhenPrepared()

            addPlayerCallback(object : PlaybackGlue.PlayerCallback() {

                override fun onPlayCompleted(glue: PlaybackGlue?) {
                    super.onPlayCompleted(glue)

                    requireActivity().finish()
                }
            })
        }

        simplePlayer.addListener(this)

        simplePlayer.addMediaItem(torrentService.mediaSourceItem())

        simplePlayer.prepare()
    }


    override fun onVideoSizeChanged(videoSize: VideoSize) {

        this.view?.let { root ->

            val svLayoutParams = this.surfaceView?.layoutParams!!
            svLayoutParams.width = root.width
            svLayoutParams.height = root.height

            this.surfaceView?.requestLayout()

            // TODO: add scaling setting
        }
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        try {

            if (isPlaying)
                requireActivity().enableKeepScreenOn()
            else
                requireActivity().disableKeepScreenOn()

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
        }
    }

    override fun torrentProgress(torrent: Torrent, status: StreamStatus) {
        super.torrentProgress(torrent, status)

        val percent = status.progress.toFormatPercent(decimals = 1)
        val progress = "  ${FSymbols.downArrow}  $percent"
        playerGlue.subtitle = playVideo.description + progress
    }


    override fun onPause() {
        super.onPause()

        try {

            playerGlue.pause()

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        try {

            playerGlue.pause()
            simplePlayer.release()

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
        }
    }


    companion object {

        const val TAG = "@TorrentVideoPlayerFragment"
        private const val PLAYER_ADAPTER_UPDATE_DELAY = 16
    }
}