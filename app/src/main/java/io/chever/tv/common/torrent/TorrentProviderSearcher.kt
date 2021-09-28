package io.chever.tv.common.torrent

import com.orhanobut.logger.Logger
import io.chever.tv.common.torrent.models.Torrent
import io.chever.tv.common.torrent.models.TorrentQuery
import io.chever.tv.common.torrent.models.TorrentSearcherResult

/**
 * TODO: document class
 */
class TorrentProviderSearcher private constructor(
    private val listProviders: List<TorrentProvider>
) {

    private var successCount = 0
    private var errorCount = 0
    private var resultListTorrents = mutableListOf<Torrent>()
    private lateinit var completedCallback: (TorrentSearcherResult) -> Unit

    private val isSearchCompleted: Boolean
        get() {
            return (successCount + errorCount) == listProviders.size
        }


    /**
     * TODO: document function
     */
    fun startSearch(query: TorrentQuery): TorrentProviderSearcher {

        listProviders.forEach { provider ->

            provider
                .launchSearch(query)
                .resultSuccess {

                    it.forEach { torrent ->

                        // Check if not has been added another same magnet
                        val isSameMagnetAdded = resultListTorrents.any { x ->
                            x.magnet == torrent.magnet
                        }

                        // Add only unique magnet to list torrents result
                        if (isSameMagnetAdded.not())
                            resultListTorrents.add(torrent)
                    }

                    successCount++
                    emmitSearchCompletedIfRequired()
                }
                .resultError {

                    errorCount++
                    emmitSearchCompletedIfRequired()

                    Logger.w(it.message!!, it)
                }
        }

        return this
    }

    /**
     * TODO: document function
     */
    fun onSearchCompleted(callback: (TorrentSearcherResult) -> Unit): TorrentProviderSearcher {
        completedCallback = callback
        return this
    }


    private fun emmitSearchCompletedIfRequired() {

        if (isSearchCompleted) {

            completedCallback.invoke(
                TorrentSearcherResult(
                    listTorrent = resultListTorrents,
                    hasError = (errorCount > 0)
                )
            )
        }
    }


    companion object {

        /**
         * TODO: document static function
         */
        fun create() = TorrentProviderSearcher(
            listProviders = TorrentProvider.listDefaultProviders
        )

        /**
         * TODO: document static function
         */
        fun create(listProviders: List<TorrentProvider>) =
            TorrentProviderSearcher(listProviders)
    }
}