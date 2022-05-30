package io.chever.tv.common.torrent

import io.chever.tv.common.torrent.enums.TorrentSite
import io.chever.tv.common.torrent.models.Torrent
import io.chever.tv.common.torrent.models.TorrentQuery
import io.chever.tv.common.torrent.providers.CineCalidadProvider
import io.chever.tv.common.torrent.providers.TorrentGalaxyProvider
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

/**
 * TODO: document class
 */
abstract class TorrentProvider(open val site: TorrentSite) {

    protected lateinit var query: TorrentQuery
    protected var result = mutableListOf<Torrent>()

    /**
     * Invoke when finished search torrents success.
     * -- NOTE --
     * Require calling so if $result empty.
     */
    protected lateinit var successCallback: (List<Torrent>) -> Unit

    /**
     * Invoke when finished search torrents with error.
     */
    protected lateinit var errorCallback: (Exception) -> Unit


    /**
     * TODO: document function
     */
    fun launchSearch(query: TorrentQuery): TorrentProvider {

        try {

            this.result.clear()

            this.query = query

            startSearchCoroutine()

        } catch (ex: Exception) {

            errorCallback.invoke(ex)
            Timber.e(ex, ex.message)
        }

        return this
    }

    /**
     * TODO: document function
     */
    fun resultSuccess(callback: (List<Torrent>) -> Unit): TorrentProvider {
        successCallback = callback
        return this
    }

    /**
     * TODO: document function
     */
    fun resultError(callback: (Exception) -> Unit): TorrentProvider {
        errorCallback = callback
        return this
    }


    /// TODO: check warning [GlobalScope] - maybe migrate to suspend fun
    @OptIn(DelicateCoroutinesApi::class)
    private fun startSearchCoroutine() = GlobalScope.launch(Dispatchers.IO) {

        try {

            startSearchTorrentInSite()

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
            errorCallback.invoke(ex)
        }
    }


    /**
     * Implement logic to search torrents in [site].
     *
     * -- Note --
     *
     * This method RUN in a COROUTINE inside [startSearchCoroutine] method.
     */
    @Throws(IOException::class)
    abstract fun startSearchTorrentInSite()


    companion object {

        /**
         * Default list of torrent providers.
         *
         * ```
         * * Add more providers if needed.
         */
        val listDefaultProviders: List<TorrentProvider> =
            listOf(

                CineCalidadProvider(),
                TorrentGalaxyProvider(),
                /// add more providers here ...
            )
    }
}