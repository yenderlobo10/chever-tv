package io.chever.tv.common.torrent.providers

import io.chever.tv.common.extension.StringExtensions.isIMDBID
import io.chever.tv.common.extension.StringExtensions.isMagnetUrl
import io.chever.tv.common.torrent.TorrentProvider
import io.chever.tv.common.torrent.enums.TorrentSite
import io.chever.tv.common.torrent.models.Torrent
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import timber.log.Timber

/**
 * TODO: document class
 */
class TorrentGalaxyProvider : TorrentProvider(site = TorrentSite.TorrentGalaxy) {

    // Only search by IMDB ID.
    override fun startSearchTorrentInSite() {

        Timber.i("Torrent Search in :: ${site.name} by [${query.idIMDB}] ::")

        val q = query.idIMDB!!

        if (q.isIMDBID().not())
            throw Exception("IMDB ID invalid [$q]")

        // Search by available languages
        Language.values().forEach { language ->

            val document = Jsoup.connect(site.url)
                .data("lang", "${language.id}")
                .data("search", q)
                .get()

            if (document is Document)
                document.trySearchTorrentsInResult()

        }

        // Finish search torrent in this provider
        successCallback.invoke(result)
    }

    // Search extension methods


    private fun Document.trySearchTorrentsInResult() {

        val searchTableRows = this.select(CssQuery.SearchTableRows.query)

        val hasElements = (searchTableRows is Elements).and(searchTableRows.isNotEmpty())

        if (hasElements)
            searchTableRows.searchItemTorrents()
    }

    private fun Elements.searchItemTorrents() {

        this.forEach { row ->

            // Check if has magnet before add to @result
            row?.child(RowPropIndex.Magnet.index)?.let {

                val aMagnet = it.children().last()
                val url = aMagnet?.attr("href") ?: ""

                if (url.isMagnetUrl()) {

                    row.createTorrentByRow(url).let { torrent ->

                        result.add(torrent)
                    }
                }
            }
        }
    }

    private fun Element.createTorrentByRow(url: String): Torrent {

        // Get title
        val tTitle = this.child(RowPropIndex.Name.index).text()

        // Get language
        val elLang = this.child(RowPropIndex.Language.index).selectFirst("img")!!
        val tLang = elLang.attr("title")

        // Get quality
        val tQuality = this.child(RowPropIndex.Quality.index).text()

        // Get size
        val tSize = this.child(RowPropIndex.Size.index).text()

        // Get downloads
        val tDownloads = this.child(RowPropIndex.Downloads.index).text() ?: "0"


        // TODO: limit add by torrent size
        return Torrent(
            magnet = url,
            site = TorrentSite.TorrentGalaxy,
            title = tTitle,
            language = tLang,
            quality = tQuality,
            size = tSize,
            downloads = tDownloads
        )
    }


    /**
     *  CSS Queries to search in sites html-element.
     */
    private enum class CssQuery(val query: String) {

        SearchTableRows(".tgxtable > .tgxtablerow"),
    }

    private enum class RowPropIndex(val index: Int) {

        Magnet(4),
        Name(3),
        Language(2),
        Quality(0),
        Size(7),
        Downloads(9),
    }

    /**
     * Languages to search.
     * - English EN[1]
     * - Spanish ES[6]
     */
    private enum class Language(val id: Int) {

        Spanish(6),
        English(1),
    }
}