package io.chever.tv.common.torrent.providers

import io.chever.tv.common.torrent.TorrentProvider
import io.chever.tv.common.torrent.enums.TorrentSite

/**
 * TODO: document class
 */
class TorrentLatinoProvider : TorrentProvider(site = TorrentSite.TorrentLatino) {

    override fun startSearchTorrentInSite() {
        // TODO("Not yet implemented")

        // Finish search torrent in this provider
        successCallback.invoke(result)
    }

    /**
     *  CSS Queries to search in sites html-element.
     */
    private enum class CssQuery(val query: String) {

    }
}