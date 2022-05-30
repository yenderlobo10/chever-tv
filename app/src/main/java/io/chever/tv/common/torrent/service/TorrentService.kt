package io.chever.tv.common.torrent.service

import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.github.se_bastiaan.torrentstream.StreamStatus
import com.github.se_bastiaan.torrentstream.Torrent
import com.github.se_bastiaan.torrentstream.TorrentOptions
import com.github.se_bastiaan.torrentstream.TorrentStream
import com.github.se_bastiaan.torrentstream.listeners.TorrentListener
import com.google.android.exoplayer2.MediaItem
import io.chever.tv.common.extension.AppConstants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jsoup.Connection
import timber.log.Timber
import java.io.File
import io.chever.tv.common.torrent.models.Torrent as TorrentFile

/**
 * TODO: document class & methods
 */
class TorrentService private constructor(
    private val fileTorrent: TorrentFile,
    private val activity: ComponentActivity,
    val isAutoStart: Boolean = false
) : TorrentListener {

    private lateinit var torrentLocationDownload: File
    private lateinit var torrentStream: TorrentStream
    private var torrentListener: TorrentServiceListener? = null
    private var attemptsTorrentStart = 1
    private var isPrepared = false

    var isReady = false
    var isReadyBuffer = false
    var isReadyPlayCalled = false

    init {

        try {

            setupService()

            if (isAutoStart) start()

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
        }
    }


    private fun setupService() {

        torrentLocationDownload = activity.getExternalFilesDir(
            Environment.DIRECTORY_DOWNLOADS
        )!!

        createTorrentStream()
    }

    private fun createTorrentStream() {

        val torrentOptions = TorrentOptions.Builder()
            .saveLocation(torrentLocationDownload)
            .removeFilesAfterStop(true)
            .autoDownload(true)
            .build()

        torrentStream = TorrentStream.init(torrentOptions)
        torrentStream.addListener(this)
    }


    override fun onStreamPrepared(torrent: Torrent?) {
        torrentListener?.torrentPrepared(torrent!!)
        isPrepared = true
    }

    override fun onStreamStarted(torrent: Torrent?) {
        torrentListener?.torrentStarted(torrent!!)
    }

    override fun onStreamError(torrent: Torrent?, ex: Exception?) {
        torrentListener?.torrentError(torrent!!, ex!!)
    }

    override fun onStreamReady(torrent: Torrent?) {
        isReady = true
        torrentListener?.torrentReady(torrent!!)
    }

    override fun onStreamProgress(torrent: Torrent?, status: StreamStatus?) {

        torrentListener?.torrentProgress(
            torrent = torrent!!,
            status = status!!
        )

        isReadyBuffer = status?.bufferProgress == 100
        val isReadyMinProgressPlay = status?.progress!! >= MIN_PROGRESS_TO_PLAY
        val isValidToEmitReadyToPLay =
            isReady.and(isReadyBuffer).and(isReadyMinProgressPlay).and(isReadyPlayCalled.not())

        if (isValidToEmitReadyToPLay) {

            torrentListener?.torrentReadyToPlay(torrent!!, status)
            isReadyPlayCalled = true
        }
    }

    override fun onStreamStopped() {
        torrentListener?.torrentStopped()
    }


    fun start(): TorrentService {

        startTorrentStream()
        return this
    }

    private fun startTorrentStream() {

        try {

            checkIfTorrentIsValidToStart()

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
            torrentListener?.torrentError(null, ex)
        }
    }

    private fun checkIfTorrentIsValidToStart() {

        val isNotAttemptsExceeded = attemptsTorrentStart <= MAX_INTENTS_START_TORRENT
        val isValidToStart = torrentStream.isStreaming.not().and(isNotAttemptsExceeded)

        if (isValidToStart) {

            torrentStream.startStream(fileTorrent.magnet)
            startWaitTorrentPrepared()

        } else {

            torrentListener?.torrentTimeout()
        }
    }

    private fun startWaitTorrentPrepared() {

        activity.lifecycleScope.launch {

            // Wait 20 seg. torrent started
            delay(MAX_SECONDS_WAIT_START_TORRENT)

            if (isPrepared.not()) {

                attemptsTorrentStart += 1

                //stopTorrentStream()
                torrentStream.removeListener(this@TorrentService)
                torrentStream.stopStream()

                delay(500)
                createTorrentStream()
                delay(250)
                startTorrentStream()
            }
        }
    }


    fun pause(): TorrentService {

        try {

            torrentStream.let {
                torrentStream.pauseSession()
            }

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
        }

        return this
    }

    fun resume(): TorrentService {

        try {

            torrentStream.let {
                torrentStream.resumeSession()
            }

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
        }

        return this
    }


    fun stop(): TorrentService {

        try {

            stopTorrentStream()

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
        }

        return this
    }

    private fun stopTorrentStream() {

        torrentStream.let {

            if (torrentStream.isStreaming) {

                torrentStream.stopStream()
                torrentStream.removeListener(this)
            }
        }
    }


    fun addListener(listener: TorrentServiceListener): TorrentService {
        torrentListener = listener
        return this
    }

    fun removeListener(): TorrentService {
        torrentListener = null
        return this
    }


    fun videoPath(): String {

        if (isReady.not()) throw Exception("Torrent can't play")

        return torrentStream.currentTorrent?.videoFile?.path!!
    }

    fun mediaSourceItem(): MediaItem {

        return MediaItem.fromUri(this.videoPath())
    }


    companion object {

        const val MIN_PROGRESS_TO_PLAY = 3f // 3%
        const val MAX_SECONDS_WAIT_START_TORRENT = 16000L // 16 seg.
        const val MAX_INTENTS_START_TORRENT = 3

        /**
         * TODO: document method
         */
        fun create(torrent: TorrentFile, activity: ComponentActivity): TorrentService =
            TorrentService(torrent, activity)

        /**
         * TODO: document method
         */
        fun createToStartImmediately(
            torrent: TorrentFile,
            activity: ComponentActivity
        ): TorrentService =
            TorrentService(torrent, activity, isAutoStart = true)

        /**
         * TODO: document method
         */
        fun Connection.withBrowserHeader(): Connection = this.header(
            "User-Agent",
            AppConstants.userAgentHeader
        )
    }
}