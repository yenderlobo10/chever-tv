package io.chever.tv.ui.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import coil.load
import com.github.se_bastiaan.torrentstream.StreamStatus
import com.github.se_bastiaan.torrentstream.Torrent
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import com.orhanobut.logger.Logger
import io.chever.tv.R
import io.chever.tv.common.extension.Extensions.showShortToast
import io.chever.tv.common.extension.NumberExtensions.toFormatPercent
import io.chever.tv.common.torrent.service.TorrentService
import io.chever.tv.common.torrent.service.TorrentServiceListener
import io.chever.tv.ui.common.models.PlayVideo

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

            Logger.e(ex.message!!, ex)
            requireContext().showShortToast(R.string.app_unknown_error_one)
            requireActivity().finish()
        }
    }


    private fun setupUI(view: View) {

        ivBackdropImage = view.findViewById(R.id.ivBackdropImage)
        tvVideoTitle = view.findViewById(R.id.tvVideoTitle)
        pbTorrent = view.findViewById(R.id.pbTorrent)
        tvTorrentPercent = view.findViewById(R.id.tvTorrentPercent)
        tvTorrentHelp = view.findViewById(R.id.tvTorrentHelp)

        tvVideoTitle.text = playVideo.title

        setupBackdropImage()

        setupTorrentService()

        Logger.d(":: PLAY VIDEO ::\n$playVideo")
    }

    private fun setupBackdropImage() {

        // Load background-image with coil
        // TODO: add default background (image)
        ivBackdropImage.load(playVideo.backdropUrl) {

            crossfade(true)
            placeholder(R.drawable.g_background_primary)
            error(R.drawable.g_background_primary)
        }
    }


    private fun setupTorrentService() {

        torrentService
            .addListener(this)
            .start()

    }


    override fun torrentPrepared(torrent: Torrent) {
        super.torrentPrepared(torrent)
        requireContext().showShortToast(":: PREPARED ::")
    }

    override fun torrentReady(torrent: Torrent) {
        super.torrentReady(torrent)

        pbTorrent.indeterminateMode = false
    }

    override fun torrentProgress(torrent: Torrent, status: StreamStatus) {
        super.torrentProgress(torrent, status)

        if (torrentService.isReady) {

            val progress = (status.progress / TorrentService.MIN_PROGRESS_TO_PLAY) * 100

            pbTorrent.progress = progress

            tvTorrentPercent.text = progress.toFormatPercent()
        }
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