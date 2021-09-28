package io.chever.tv.ui.player

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.orhanobut.logger.Logger
import io.chever.tv.R
import io.chever.tv.common.extension.Extensions.showShortToast
import io.chever.tv.common.torrent.service.TorrentService
import io.chever.tv.ui.common.models.PlayVideo

/**
 * TODO: document class
 */
class TorrentPlayerActivity : FragmentActivity() {

    private lateinit var playVideo: PlayVideo
    private lateinit var torrentService: TorrentService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {

            setupUI(savedInstanceState)

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
            showShortToast(R.string.app_unknown_error_one)
        }
    }

    private fun setupUI(savedInstanceState: Bundle?) {

        initArguments()

        createTorrentService()

        if (savedInstanceState == null) showTorrentLoad()
    }

    private fun initArguments() {

        val params = intent?.extras ?: throw IllegalArgumentException()

        playVideo = params.getSerializable(PARAM_PLAY_VIDEO) as PlayVideo
    }

    private fun createTorrentService() {

        torrentService = TorrentService.create(
            torrent = playVideo.torrent,
            activity = this
        )
    }

    private fun showTorrentLoad() {

        supportFragmentManager.beginTransaction()
            .replace(
                android.R.id.content,
                TorrentLoadFragment(playVideo, torrentService),
                TorrentLoadFragment.TAG
            )
            .commit()
    }


    override fun onDestroy() {

        try {

            torrentService.stop()

        } catch (ex: Exception) {
            Logger.e(ex.message!!, ex)
        }

        super.onDestroy()
    }


    companion object {

        const val PARAM_PLAY_VIDEO = "chever-io-play-video"
    }
}