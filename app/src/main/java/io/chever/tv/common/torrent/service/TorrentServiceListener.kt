package io.chever.tv.common.torrent.service

import com.github.se_bastiaan.torrentstream.StreamStatus
import com.github.se_bastiaan.torrentstream.Torrent
import io.chever.tv.common.extension.NumberExtensions.toFormat
import io.chever.tv.common.extension.NumberExtensions.toFormatPercent
import timber.log.Timber

/**
 * TODO: document interface
 */
interface TorrentServiceListener {

    fun torrentStarted(torrent: Torrent) {
        Timber.i(":: TORRENT STARTED ::")
    }

    fun torrentPrepared(torrent: Torrent) {
        Timber.i(":: TORRENT PREPARED ::")
    }

    fun torrentProgress(torrent: Torrent, status: StreamStatus) {

        println(":: TORRENT PROGRESS ::")
        val speed = (status.downloadSpeed.toFloat() / 1024 / 1024).toFormat(decimals = 1)
        val progress = status.progress.toFormatPercent(decimals = 1)
        println(
            "" +
                    "Speed: $speed MB/s | " +
                    "Progress: $progress | " +
                    "Seeds: ${status.seeds} | " +
                    "Buffer: ${status.bufferProgress}"
        )
    }

    fun torrentReady(torrent: Torrent) {
        Timber.i(":: TORRENT READY ::")
    }

    fun torrentReadyToPlay(torrent: Torrent, status: StreamStatus) {
        println(":: TORRENT READY TO PLAY ::")
    }

    fun torrentStopped() {
        Timber.i(":: TORRENT STOPPED ::")
    }

    fun torrentError(torrent: Torrent?, ex: Exception) {
        Timber.w(":: TORRENT ERROR ::", ex)
    }

    fun torrentTimeout() {
        Timber.w(":: TORRENT TIMEOUT ::")
    }
}