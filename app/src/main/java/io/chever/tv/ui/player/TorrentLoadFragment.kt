package io.chever.tv.ui.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import coil.load
import coil.size.Scale
import com.github.se_bastiaan.torrentstream.StreamStatus
import com.github.se_bastiaan.torrentstream.Torrent
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import io.chever.tv.R
import io.chever.tv.common.extension.Extensions.showShortToast
import io.chever.tv.common.extension.NumberExtensions.toFormat
import io.chever.tv.common.extension.NumberExtensions.toFormatPercent
import io.chever.tv.common.torrent.service.TorrentService
import io.chever.tv.common.torrent.service.TorrentServiceListener
import io.chever.tv.ui.common.models.PlayVideo
import timber.log.Timber

/**
 * TODO: document class
 */
class TorrentLoadFragment(
    private val playVideo: PlayVideo,
    private val torrentService: TorrentService
) : Fragment(), TorrentServiceListener {

    private lateinit var ivBackdropImage: ImageView
    private lateinit var tvVideoTitle: TextView
    private lateinit var pbTorrent: CircularProgressBar
    private lateinit var tvTorrentPercent: TextView
    private lateinit var tvTorrentStatus: TextView
    private lateinit var tvTorrentHelp: TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(
            R.layout.fragment_load_torrent,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {

            setupUI(view)

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
            requireContext().showShortToast(R.string.app_unknown_error_one)
            requireActivity().finish()
        }
    }


    private fun setupUI(view: View) {

        ivBackdropImage = view.findViewById(R.id.ivBackdropImage)
        tvVideoTitle = view.findViewById(R.id.tvVideoTitle)
        pbTorrent = view.findViewById(R.id.pbTorrent)
        tvTorrentPercent = view.findViewById(R.id.tvTorrentPercent)
        tvTorrentStatus = view.findViewById(R.id.tvTorrentStatus)
        tvTorrentHelp = view.findViewById(R.id.tvTorrentHelp)

        tvVideoTitle.text = playVideo.title

        setupBackdropImage()

        setupTorrentService()

        Timber.d(":: PLAY VIDEO ::\n$playVideo")
    }

    private fun setupBackdropImage() {

        // Load background-image with coil
        // TODO: add default background (image)
        ivBackdropImage.load(playVideo.backdropUrl) {

            crossfade(true)
            placeholder(R.drawable.g_background_primary)
            error(R.drawable.g_background_primary)
            scale(Scale.FILL)
        }
    }


    private fun setupTorrentService() {

        torrentService
            .addListener(this)
            .start()

    }


    override fun torrentPrepared(torrent: Torrent) {
        super.torrentPrepared(torrent)

        pbTorrent.indeterminateMode = false
        tvTorrentHelp.text = "Redactando el guión ..."
    }

    override fun torrentReady(torrent: Torrent) {
        super.torrentReady(torrent)

        tvTorrentHelp.text = "Grabando escenas ..."
    }

    override fun torrentProgress(torrent: Torrent, status: StreamStatus) {
        super.torrentProgress(torrent, status)

        updateTorrentStatusInfo(status)

        val progress = when {

            torrentService.isReady ->
                (status.progress / TorrentService.MIN_PROGRESS_TO_PLAY) * 100

            else -> status.bufferProgress.toFloat()
        }

        pbTorrent.progress = progress

        if (progress <= 100f)
            tvTorrentPercent.text = progress.toFormatPercent()
    }

    private fun updateTorrentStatusInfo(status: StreamStatus) {

        val speed = (status.downloadSpeed.toFloat() / 1024 / 1024).toFormat(decimals = 1)
        val progress = status.progress.toFormatPercent(decimals = 1)

        tvTorrentStatus.text = "$speed MB/s  |  $progress  |  (${status.seeds})"
    }

    override fun torrentReadyToPlay(torrent: Torrent, status: StreamStatus) {
        super.torrentReadyToPlay(torrent, status)

        torrentService.removeListener()

        parentFragmentManager.beginTransaction()
            .replace(
                android.R.id.content,
                TorrentVideoPlayerFragment(playVideo, torrentService),
                TorrentVideoPlayerFragment.TAG
            )
            .commit()
    }

    override fun torrentError(torrent: Torrent?, ex: Exception) {
        super.torrentError(torrent, ex)
        requireContext().showShortToast(":: ERROR ::")
        requireActivity().finish()
    }

    override fun torrentTimeout() {
        super.torrentTimeout()
        requireContext().showShortToast(":: TIMEOUT ::")
        requireActivity().finish()
    }


    companion object {

        const val TAG = "@TorrentLoadFragment"
    }
}