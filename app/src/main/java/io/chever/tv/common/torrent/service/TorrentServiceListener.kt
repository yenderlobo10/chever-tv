package io.chever.tv.common.torrent.service

import com.github.se_bastiaan.torrentstream.StreamStatus
import com.github.se_bastiaan.torrentstream.Torrent
import com.orhanobut.logger.Logger
import io.chever.tv.common.extension.NumberExtensions.toFormat
import io.chever.tv.common.extension.NumberExtensions.toFormatPercent

/**
 * TODO: document interface
 */
interface TorrentServiceListener {

    fun torrentStarted(torrent: Torrent) {
        Logger.i(":: TORRENT STARTED ::")
    }

    fun torrentPrepared(torrent: Torrent) {
        Logger.i(":: TORRENT PREPARED ::")
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
        Logger.i(":: TORRENT READY ::")
    }

    fun torrentReadyToPlay(torrent: Torrent, status: StreamStatus) {
        println(":: TORRENT READY TO PLAY ::")
    }

    fun torrentStopped() {
        Logger.i(":: TORRENT STOPPED ::")
    }

    fun torrentError(torrent: Torrent?, ex: Exception) {
        Logger.e(":: TORRENT ERROR ::", ex)
    }

    fun torrentTimeout() {
        Logger.e(":: TORRENT TIMEOUT ::")
    }
}