package io.chever.tv.ui.player

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.leanback.app.VideoSupportFragment
import androidx.leanback.app.VideoSupportFragmentGlueHost
import androidx.leanback.media.PlaybackGlue
import androidx.leanback.media.PlaybackTransportControlGlue
import com.github.se_bastiaan.torrentstream.StreamStatus
import com.github.se_bastiaan.torrentstream.Torrent
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ext.ffmpeg.FfmpegLibrary
import com.google.android.exoplayer2.ext.leanback.LeanbackPlayerAdapter
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.SubtitleView
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.video.VideoSize
import io.chever.tv.R
import io.chever.tv.common.extension.Extensions.disableKeepScreenOn
import io.chever.tv.common.extension.Extensions.enableKeepScreenOn
import io.chever.tv.common.extension.Extensions.showShortToast
import io.chever.tv.common.extension.FSymbols
import io.chever.tv.common.extension.NumberExtensions.toFormatPercent
import io.chever.tv.common.torrent.service.TorrentService
import io.chever.tv.common.torrent.service.TorrentServiceListener
import io.chever.tv.ui.common.models.PlayVideo
import timber.log.Timber
import java.util.*

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
    private lateinit var playerGlue: PlaybackTransportControlGlue<LeanbackPlayerAdapter>
    private lateinit var glueHost: VideoSupportFragmentGlueHost
    private lateinit var trackSelector: DefaultTrackSelector
    private lateinit var subtitleView: SubtitleView


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

        // TODO: Add video decoder extensions [check this]
        val renderFactory = DefaultRenderersFactory(requireContext()).apply {
            setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
        }


        trackSelector = DefaultTrackSelector(requireContext())
//        trackSelector.parameters = DefaultTrackSelector.ParametersBuilder(requireContext())
//            //.setSelectUndeterminedTextLanguage(true)
//            .build()

        simplePlayer = ExoPlayer.Builder(requireContext(), renderFactory)
            .setTrackSelector(trackSelector)
            .build()

        subtitleView = requireActivity().findViewById(R.id.leanback_subtitles)
        //simplePlayer.textComponent?.addTextOutput(subtitleView)

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
        simplePlayer.addListener(subtitleView)

        // TODO: only test code

        // Matrix: https://www.opensubtitles.com/download/D977D8328D345FC9F2A7AA67154B6E2AC8D37479124C2B49C3033249FE461FE1E221C3EFE22D604B0E26F0C845435DED4EA9E12899F51D48E166B177AEEA55858AB2D9BD9A8FAA182F9E789E4E0586F7FC98EA0E887D2D91B4ED0F9B539C7F0594F3CC4F2BA8BEB5DDADEA5D02731BEF6F40B5CCA1060A04F1D1240CB5B30DEBBDBD6B43F4F1DB209960809CBB014EB67193B0CB6E755A24649FC2AF29C361D2F324953FD735DAAACC33C1C5B2313453235FB8ED8D77150F8744ED1BC13872D077BFDC479C1A50C494530DCE52FA0ED4263AB58BD943E204AC5944871DCD7EACDCC0821767C7D4AC5E88960054CA433EA3971E78C5F655DDAF505B6C9250E056EAEDACB4D80910FDC59E4E3FD390C0E5/subfile/The.Matrix.1999.REMASTERED.1080p.BluRay.X264-AMIABLE.es.srt
        // Interstellar: https://www.opensubtitles.com/download/D977D8328D345FC9224F3505817B4622FA5A7DC684CA1FBF796084246466C304F5E5896EE1CC2BCC53564D712857273B1CFE7DAB15C22C883AB8858CE3FA3866B00958B57DBEBF465F7EFC88B5E3A057F99BDBA4E825F48509FFF76BAE0527425992A131CB8788500E9C5C0E49BE33FD4E2D875A5E96C2D63C40A3E160DE10CF7F231379D85177B1C92AF944790761EB68775FD9E1BEE2ED7332ED73E8423716CE476BBD7B3683C356F2F5F7118AC5725EE265E8DF0AD752EB1166A5B392B93062F7CF0F904B81BD0006A086EAC5894B007D4215944372BCC13ED7A7AD14E24BC6CE70DC89F614B54D437184C8D9F21FCE168907D1568035F6BDDA74F7CAF4952F23C59DF31CC475/subfile/Interstellar.2014.720p.BluRay.x264.DTS-WiKi.es.srt
        val subtitleUrl =
            "https://www.opensubtitles.com/download/E1D0C2FDEC35DE99DCFA8102B2422CEFB461CF2274B274FA57EB4744BE2ADE07EA99A2B26FB7E3EE8E5C8EA638F053EEFE08DE7852D8DDE13D769D6A4082AB8735082D34F5F92F7C7B2AC0B4E72541ADABF241BE7CF86EF8E2918F8B7E81C6CF5E97280EDE3512CFABBDAA8A920374A18EB177D23AD12026072B76E951672258C73D9CDB958D0EC2CEF65DEFE5648CA7FB19E1D53F87748B2DC4C51BF4C086FACEDEB826D900B5A7647E0259AB5CCCC5D7D8F366381BEED921E9A1A6B41C23A224BFFA0D1E927AB34B9E7FDF4CCBBACD36269DBEDAC40FDD8AE114E44783206992926C228A01962F9AAC3D295ABA038001EF4AD2A7717512DFA03B64EFD921C299B185F2D033348F/subfile/Dune%202021%201080p%20HDRip%20X264%20AC3-EVO.srt"

//        val subtitles = MediaItem.Subtitle(
//            Uri.parse(subtitleUrl),
//            MimeTypes.APPLICATION_SUBRIP,
//            "es",
//            C.SELECTION_FLAG_DEFAULT
//        )
        val subtitles = MediaItem.SubtitleConfiguration.Builder(Uri.parse(subtitleUrl))
            .setMimeType(MimeTypes.APPLICATION_SUBRIP)
            .setLanguage("es")
            .setSelectionFlags(C.SELECTION_FLAG_DEFAULT)
            .build()

        val mediaItem = MediaItem.Builder()
            .setUri(torrentService.videoPath())
            //.setSubtitles(arrayListOf(subtitles))
            .setSubtitleConfigurations(listOf(subtitles))
            .build()


        simplePlayer.addMediaItem(mediaItem)

        /// only test code


        simplePlayer.prepare()
    }


    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)

        try {


        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
        }
    }


    fun trackSelectorTest() {

        //renderIndex = 0 para video, = 1 para audio, = 2 para subtitulos/texto
        val renderIndexSubtitles = 2

        val mti = trackSelector.currentMappedTrackInfo ?: return

        val subtitlesGroups = mti.getTrackGroups(renderIndexSubtitles)

        val currentTrackGroups = simplePlayer.currentTrackSelections
        val currentSubtitles = currentTrackGroups.get(renderIndexSubtitles)

        for (groupIndex in 0 until subtitlesGroups.length) {

            val group = subtitlesGroups.get(groupIndex)

            for (trackIndex in 0 until group.length) {

                val format = group.getFormat(trackIndex)

                val currentFormat = currentSubtitles?.getFormat(trackIndex)

                val isSame = currentFormat == format

                if (!isSame) {

                    val override = DefaultTrackSelector.SelectionOverride(
                        groupIndex, trackIndex
                    )
                    val params = trackSelector.buildUponParameters()
                        .setSelectionOverride(
                            renderIndexSubtitles,
                            subtitlesGroups,
                            override
                        )

                    trackSelector.setParameters(
                        params.setRendererDisabled(
                            renderIndexSubtitles,
                            true
                        )
                    )

                    println(":: ADD SUBTITLES TRACK :: ($trackIndex)")
                    requireContext().showShortToast(":: ADD SUBTITLES TRACK :: (${format.language})")
                }
            }
        }

        requireContext().showShortToast("SUBVIEW IS ${subtitleView.visibility}")
    }

    inner class TrackInfo(
        trackSelector: DefaultTrackSelector,
        trackType: Int
    ) {
        val renderList = mutableListOf<Int>()
        val trackList = mutableListOf<TrackEntry>()

        init {
            initData()
        }

        private fun initData() {

            val mti = trackSelector.currentMappedTrackInfo ?: return


        }
    }

    inner class TrackEntry(
        val ixRenderer: Int,
        val ixTrackGroup: Int,
        val ixTrack: Int,
        val format: Format,
        val description: String,
    )


    // trackSelection = current selection. -1 = disabled, -2 = leave as is
    // disable = true : Include disabled in the rotation
    // doChange = true : select a new track, false = leave same track
    // Return = new track selection.
    fun trackSelector(
        trackType: Int, trackSelection: Int,
        msgOn: Int, msgOff: Int, disable: Boolean, doChange: Boolean
    ): Int {
        // optionList array - 0 = renderer, 1 = track group, 2 = track
        var trackSelection = trackSelection
        val optionList = ArrayList<IntArray>()
        val renderList = ArrayList<Int>()
        val formatList = ArrayList<Format>()

        val mti = trackSelector.currentMappedTrackInfo
        val isPlaying = playerGlue.isPlaying

        if (mti == null) return -1

        for (rendIx in 0 until mti.rendererCount) {

            if (mti.getRendererType(rendIx) == trackType) {

                renderList.add(rendIx)

                val tga = mti.getTrackGroups(rendIx)

                for (tgIx in 0 until tga.length) {

                    val tg = tga[tgIx]

                    for (trkIx in 0 until tg.length) {

                        val selection = IntArray(3)
                        // optionList array - 0 = renderer, 1 = track group, 2 = track
                        selection[0] = rendIx
                        selection[1] = tgIx
                        selection[2] = trkIx

                        optionList.add(selection)
                        formatList.add(tg.getFormat(trkIx))
                    }
                }

                break
            }
        }

        val msg = StringBuilder()

        if (doChange) {

            when {
                (trackSelection == -2) -> trackSelection = -1

                (optionList.size == 0) -> trackSelection = -1

                (++trackSelection >= optionList.size) ->
                    trackSelection = if (disable) -1 else 0
            }
        }

        when {
            (trackSelection >= 0) -> {

                val selection = optionList[trackSelection]
                val override = DefaultTrackSelector.SelectionOverride(
                    selection[1],
                    selection[2]
                )
                val tga = mti.getTrackGroups(selection[0])

                var params = trackSelector
                    .buildUponParameters()
                    .setSelectionOverride(selection[0], tga, override)

                if (disable) params = params.setRendererDisabled(selection[0], false)

                // This line causes playback to pause when enabling subtitle
                trackSelector.setParameters(params)

                var language = formatList[trackSelection].language

                language = if (language?.isBlank()!!) String() else {
                    val locale = Locale(language)
                    val langDesc = locale.displayLanguage
                    "($langDesc)"
                }

                if (msgOn > 0) msg.append(
                    requireActivity().getString(
                        msgOn,
                        trackSelection + 1, language
                    )
                )
            }

            (trackSelection == -1) -> {

                if (optionList.size > 0) {

                    for (ix in renderList.indices) {
                        trackSelector.setParameters(
                            trackSelector
                                .buildUponParameters()
                                .setRendererDisabled(renderList[ix], true)
                        )
                    }
                }

                if (msgOff > 0) msg.append(requireActivity().getString(msgOff))
            }
        }

//        if (msg.isNotEmpty()) {
//            showToast(msg.toString())
//        }

        // For some reason changing the subtitle pauses playback. This fixes that.
        if (trackType == C.TRACK_TYPE_TEXT && isPlaying) {

            playerGlue.pause()
            playerGlue.play()
        }

        return trackSelection
    }


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
        val progress = "  ${FSymbols.downArrow}  $percent"
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