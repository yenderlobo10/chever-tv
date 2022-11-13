package io.chever.tv.common.torrent.providers

import androidx.core.text.isDigitsOnly
import io.chever.tv.common.extension.StringExtensions.deleteShortWords
import io.chever.tv.common.extension.StringExtensions.isHttpUrl
import io.chever.tv.common.extension.StringExtensions.isMagnetUrl
import io.chever.tv.common.torrent.TorrentProvider
import io.chever.tv.common.torrent.enums.TorrentSite
import io.chever.tv.common.torrent.models.Torrent
import io.chever.tv.common.torrent.service.TorrentService.Companion.withBrowserHeader
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import timber.log.Timber
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * TODO: document class
 */
class HackTorrentProvider : TorrentProvider(site = TorrentSite.HackTorrent) {

    private var isFound = false
    private var activeQuery = ""


    override fun startSearchTorrentInSite() {

        Timber.i("Torrent Search in :: ${site.name} by [${query.idIMDB}] ::")

        for (q in query.queries) {

            activeQuery = q.trim().lowercase()
            val search = q.deleteShortWords()

            val document = Jsoup.connect(site.url)
                .withBrowserHeader()
                .data("s", search)
                .get()


            if (document is Document)
                document.checkIfSearchHasPagination()

            if (isFound) {
                isFound = false
                break
            }
        }

        // TODO: add alternative search (title no match)

        // Invoke when finished search torrents.
        // -- NOTE --
        // Is probably this emmit empty $result.
        successCallback.invoke(result)
    }


    // Search extension methods


    private fun Document.checkIfSearchHasPagination() {

        val elPagination = this.selectFirst(CssQuery.CatalogPagination.query)

        val hasPagination = elPagination is Element

        if (hasPagination) {

            val paginationItems = elPagination?.select(CssQuery.CatalogPaginationItem.query)
            val elTextLastNumberPage = paginationItems?.last()?.text()
            val maxPage = elTextLastNumberPage?.toInt()!!

            searchInCatalogWithPagination(maxPage)

        } else {

            this.searchCatalogInDocument()
        }
    }

    private fun searchInCatalogWithPagination(maxPage: Int) {

        val search = activeQuery.deleteShortWords()

        for (n in 1..maxPage) {

            val document = Jsoup.connect("${site.url}page/$n/")
                .data("s", search)
                .get()

            if (document is Document)
                document.searchCatalogInDocument()

            if (isFound) break
        }
    }


    private fun Document.searchCatalogInDocument() {

        val elements = this.select(CssQuery.CatalogItem.query)

        val hasElements = (elements is Elements).and(elements.isNotEmpty())

        if (hasElements)
            elements.searchItemInCatalogElements()
    }

    private fun Elements.searchItemInCatalogElements() {

        this.forEach { element ->

            val title = element?.selectFirst(CssQuery.CatalogItemTitle.query)

            val titleLink = title?.selectFirst(CssQuery.CatalogItemTitleLink.query)

            val linkText = titleLink?.text()?.trim()?.lowercase(Locale.ROOT)!!

            val isItemMatched = (linkText == activeQuery).or(
                activeQuery.deleteShortWords().split(' ').any { word ->
                    linkText.contains(
                        word
                    )
                })

            if (isItemMatched) {

                val url = titleLink.attr("href")

                if (url.isHttpUrl()) {

                    val isItemDetailMatched = isMatchDetailItemSearched(url)

                    if (isItemDetailMatched) {

                        searchTorrentsInItemDocument(url)
                        isFound = true
                        return // break => item found
                    }
                }
            }
        }
    }

    private fun isMatchDetailItemSearched(itemUrl: String): Boolean {

        val document = Jsoup.connect(itemUrl).get()

        if (document !is Document) return false

        val metaElements = document.select(CssQuery.ItemDetailsMeta.query)

        val hasElements = (metaElements is Elements).and(metaElements.size > 3)

        if (!hasElements) return false

        val elYearText = metaElements[1]?.text()
        val elRuntimeText = metaElements[2]?.text()

        val metaYear = StringUtils.getDigits(elYearText)
        val metaRuntime = StringUtils.getDigits(elRuntimeText)

        if (metaYear?.isEmpty()!! || metaYear.isDigitsOnly().not()) return false
        if (metaRuntime?.isEmpty()!! || metaRuntime.isDigitsOnly().not()) return false

        // Check [Year]
        val isMetaYearMatched = metaYear.toInt() == query.year

        // Check [Runtime] percent diff
        val metaRuntimeHours = NumberUtils.toInt((
                metaRuntime.getOrElse(0) { '0' }.toString()
                        + metaRuntime.getOrElse(1) { '0' }
                ),
            0
        )
        val metaRuntimeMinutes = NumberUtils.toInt(
            metaRuntime.getOrElse(2) { '0' }.toString()
                    + metaRuntime.getOrElse(3) { '0' },
            0
        )

        val metaRuntimeInMinutes = (metaRuntimeHours * 60) + metaRuntimeMinutes
        val minRuntime = min(metaRuntimeInMinutes, query.duration)
        val maxRuntime = max(metaRuntimeInMinutes, query.duration)
        val percentRuntime = abs((minRuntime.toFloat() / maxRuntime.toFloat()))
        val isMetaRuntimeMatched = (percentRuntime >= 0.75f)

        return isMetaYearMatched.and(isMetaRuntimeMatched)
    }

    private fun searchTorrentsInItemDocument(itemUrl: String) {

        val document = Jsoup.connect(itemUrl).get()

        document.let document@{ docHtml ->

            val resultTable = docHtml.selectFirst(CssQuery.ItemTorrentsTable.query)

            resultTable?.let table@{

                val resultTableRows = resultTable.select(CssQuery.ItemTorrentsTableRows.query)

                val title = docHtml.selectFirst(CssQuery.ItemDetailsTitle.query)?.text()

                resultTableRows.searchTorrentsInItemElements(
                    torrentTitle = title!!
                )
            }
        }
    }

    private fun Elements.searchTorrentsInItemElements(torrentTitle: String) {

        val title = torrentTitle.trim().lowercase()

        this.forEachIndexed { _, row ->

            val columnLink = row?.selectFirst(CssQuery.ItemTorrentsDownloadLink.query)

            val tokenUrl = columnLink?.attr("href")!!

            // Search | Set torrent magnet
            val magnet = when {

                tokenUrl.trim().isMagnetUrl() -> tokenUrl.trim()

                else -> ""
            }

            // Create & Add torrent in result list
            if (magnet.isNotBlank())
                row.createTorrentItemFromElementInResult(
                    title = title,
                    magnet = magnet
                )
        }
    }

    @Deprecated("Not required (maybe in future)")
    private fun searchMagnetInItemTorrentDocument(tokenUrl: String): String {

        val newTokenUrl = tokenUrl.replace(
            PATTERN_URL_TOKEN_SEARCH,
            PATTERN_URL_TOKEN_REPLACE
        )

        val document = Jsoup.connect(newTokenUrl).get()

        document.let {

            val resultLink = it.body().selectFirst(CssQuery.ItemTokenUrl.query)

            resultLink?.attr("href")?.let { url ->
                return if (url.isMagnetUrl()) url else ""
            }
        }

        return ""
    }


    private fun Element.createTorrentItemFromElementInResult(
        title: String,
        magnet: String
    ) {

        val rowColumns = this.select(CssQuery.ItemTorrentsRowColumns.query)

        val tdQuality = rowColumns.elementAt(0)?.text() ?: ""
        val tdLanguage = rowColumns.elementAt(1)?.text() ?: ""
        val tdDate = rowColumns.elementAt(2)?.text() ?: ""
        val tdSize = rowColumns.elementAt(3)?.text() ?: ""
        val tdDownloads = rowColumns.elementAt(5)?.text() ?: "0"

        val itemTorrent = Torrent(
            site = site,
            title = title,
            magnet = magnet,
            quality = tdQuality,
            language = tdLanguage,
            date = tdDate,
            size = tdSize,
            downloads = tdDownloads
        )

        // Add torrent to $result of search
        result.add(itemTorrent)
    }


    /**
     *  CSS Queries to search in sites html-element.
     */
    private enum class CssQuery(val query: String) {

        CatalogItem("div.container div.card"),
        CatalogItemTitle(".card__title"),
        CatalogItemTitleLink("a"),
        CatalogPagination("div.container ul.pagination"),
        CatalogPaginationItem("li > a:not(.next)"),
        ItemDetailsMeta(".card.card--details ul.card__meta > li"),
        ItemTorrentsTable(".content.torrents > .container table"),
        ItemTorrentsTableRows("tbody > tr"),
        ItemDetailsTitle(".section.details .details__title"),
        ItemTorrentsDownloadLink("td > a.dwnld"),
        ItemTorrentsRowColumns("> td"),
        ItemTokenUrl("div > a"),
    }


    companion object {

        const val PATTERN_URL_TOKEN_SEARCH = "#"
        const val PATTERN_URL_TOKEN_REPLACE = "?k="
    }
}