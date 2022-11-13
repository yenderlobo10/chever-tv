package io.chever.tv.ui.player

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost
import androidx.leanback.media.PlaybackGlue
import com.github.se_bastiaan.torrentstream.StreamStatus
import com.github.se_bastiaan.torrentstream.Torrent
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.leanback.LeanbackPlayerAdapter
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.CaptionStyleCompat
import com.google.android.exoplayer2.ui.SubtitleView
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.video.VideoSize
import io.chever.tv.R
import io.chever.tv.api.opensubtitles.domain.models.OSDownloadResult
import io.chever.tv.common.extension.Extensions.disableKeepScreenOn
import io.chever.tv.common.extension.Extensions.enableKeepScreenOn
import io.chever.tv.common.extension.Extensions.showShortToast
import io.chever.tv.common.extension.FSymbols
import io.chever.tv.common.extension.NumberExtensions.toFormat
import io.chever.tv.common.extension.NumberExtensions.toFormatPercent
import io.chever.tv.common.torrent.service.TorrentService
import io.chever.tv.common.torrent.service.TorrentServiceListener
import io.chever.tv.ui.common.models.PlayVideo
import io.chever.tv.ui.player.subtitles.SubtitlesActivity
import io.chever.tv.ui.player.subtitles.SubtitlesPreferenceScreen
import timber.log.Timber

/**
 * TODO: document class
 */
class TorrentVideoPlayerFragment(
    private val playVideo: PlayVideo,
    private val torrentService: TorrentService
) : VideoSupportFragment(),
    TorrentServiceListener,
    Player.Listener {

    private lateinit var simplePlayer: ExoPlayer
    private lateinit var playerAdapter: LeanbackPlayerAdapter
    private lateinit var playerGlue: VideoPlayerGlue<LeanbackPlayerAdapter>
    private lateinit var glueHost: VideoSupportFragmentGlueHost
    private lateinit var trackSelector: DefaultTrackSelector
    private lateinit var subtitleView: SubtitleView
    private lateinit var mediaItem: MediaItem


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {

            setupVideoPlayer()

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
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

        val renderFactory = DefaultRenderersFactory(requireContext()).apply {
            setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
        }


        trackSelector = DefaultTrackSelector(requireContext())

        simplePlayer = ExoPlayer.Builder(requireContext(), renderFactory)
            .setTrackSelector(trackSelector)
            .build()

        subtitleView = requireActivity().findViewById(R.id.leanback_subtitles)
        subtitleView.apply {
            setStyle(
                CaptionStyleCompat(
                    Color.WHITE,
                    Color.TRANSPARENT,
                    Color.TRANSPARENT,
                    CaptionStyleCompat.EDGE_TYPE_DROP_SHADOW,
                    Color.BLACK,
                    null
                )
            )
        }

        playerAdapter =
            LeanbackPlayerAdapter(
                requireActivity(), simplePlayer,
                PLAYER_ADAPTER_UPDATE_DELAY
            )

        playerGlue = VideoPlayerGlue(requireActivity(), playerAdapter)
        playerGlue.apply {

            host = glueHost

            title = playVideo.title + " (${playVideo.year})"
            subtitle = playVideo.description

            isSeekEnabled = true

            playWhenPrepared()

            addPlayerCallback(object : PlaybackGlue.PlayerCallback() {

                override fun onPlayCompleted(glue: PlaybackGlue?) {
                    super.onPlayCompleted(glue)

                    requireActivity().finish()
                }
            })

            addOnActionSubtitlesClicked {
                activitySubtitlesIntent.launch(
                    Intent(
                        requireContext(),
                        SubtitlesActivity::class.java
                    ).apply {
                        putExtra(SubtitlesActivity.PARAM_PLAY_VIDEO, playVideo)
                    }
                )
            }
        }

        simplePlayer.addListener(this)
        simplePlayer.addListener(subtitleView)

        mediaItem = MediaItem.Builder()
            .setUri(torrentService.videoPath())
            .build()

        simplePlayer.addMediaItem(mediaItem)

        simplePlayer.prepare()
    }

    private val activitySubtitlesIntent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

            if (it.resultCode == Activity.RESULT_OK) {

                it.data?.let { data ->

                    val downloadSubtitle = data.getSerializableExtra(
                        SubtitlesPreferenceScreen.PARAM_SUBTITLE_DOWNLOAD
                    ) as OSDownloadResult

                    val subtitle = createSubtitles(url = downloadSubtitle.link)
                    simplePlayer.setMediaItem(
                        mediaItem.buildUpon()
                            .setSubtitleConfigurations(listOf(subtitle))
                            .build(),
                        false
                    )

                    requireContext().showShortToast("SUBS CHANGED")
                }
            }

            if (simplePlayer.isPlaying.not())
                simplePlayer.play()
        }

    private fun createSubtitles(url: String) = MediaItem.SubtitleConfiguration
        .Builder(Uri.parse(url))
        .setMimeType(MimeTypes.APPLICATION_SUBRIP)
        .setLanguage("es")
        .setSelectionFlags(C.SELECTION_FLAG_DEFAULT)
        .build()

    override fun onVideoSizeChanged(videoSize: VideoSize) {

        this.view?.let { root ->

            val svLayoutParams = this.surfaceView?.layoutParams!!
            svLayoutParams.width = root.width
            svLayoutParams.height = root.height

            this.surfaceView?.requestLayout()

            Timber.i(":: VIDEO SIZE CHANGED ::")
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

            Timber.e(ex, ex.message)
        }
    }

    override fun torrentProgress(torrent: Torrent, status: StreamStatus) {
        super.torrentProgress(torrent, status)

        val percent = status.progress.toFormatPercent(decimals = 1)
        val speed = (status.downloadSpeed.toFloat() / 1024 / 1024).toFormat(decimals = 1)

        val progress =
            "  ${FSymbols.downArrow}  $percent | $speed MB/s | ${FSymbols.eyes} ${status.seeds}"
        playerGlue.subtitle = playVideo.description + progress
    }


    override fun onPause() {
        super.onPause()

        try {

            playerGlue.pause()

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        try {

            playerGlue.pause()
            simplePlayer.release()

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
        }
    }


    companion object {

        const val TAG = "@TorrentVideoPlayerFragment"
        private const val PLAYER_ADAPTER_UPDATE_DELAY = 16
    }
}