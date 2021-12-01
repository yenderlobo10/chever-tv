package io.chever.tv.common.torrent.providers

import io.chever.tv.common.extension.StringExtensions.deleteShortWords
import io.chever.tv.common.extension.StringExtensions.isHttpUrl
import io.chever.tv.common.extension.StringExtensions.isMagnetUrl
import io.chever.tv.common.torrent.TorrentProvider
import io.chever.tv.common.torrent.enums.TorrentSite
import io.chever.tv.common.torrent.models.Torrent
import org.apache.commons.lang3.math.NumberUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import timber.log.Timber
import java.util.*

@Deprecated("Required captcha resolution.")
class MiTorrentProvider : TorrentProvider(TorrentSite.MiTorrent) {

    private var isFound = false
    private var activeQuery = ""

    override fun startSearchTorrentInSite() {

        Timber.i("Torrent Search in :: ${site.name} by ${query.queries} ::")

        for (q in query.queries) {

            activeQuery = q.trim().lowercase()
            val search = q.deleteShortWords()

            val document = Jsoup.connect(site.url)
                .data("s", search)
                .get()

            if (document is Document)
                document.checkIfResultCatalogHasPagination()

            if (isFound) {
                isFound = false
                break
            }
        }

        // Finish search torrent in this provider
        successCallback.invoke(result)
    }

    // Search extension methods


    private fun Document.checkIfResultCatalogHasPagination() {

        val elPagination = this.selectFirst(CssQuery.CatalogPagination.query)

        val hasPagination =
            (elPagination is Element).and((elPagination?.childNodeSize() == 0).not())

        if (hasPagination) {

            val paginationItems = elPagination?.select(CssQuery.CatalogPaginationItem.query)
            val elTextLastNumberPage = paginationItems?.last()?.text()

            elTextLastNumberPage?.let {

                if (NumberUtils.isParsable(it))
                    searchTorrentInResultCatalogWithPagination(
                        maxPage = it.toInt()
                    )
            }

        } else {

            this.searchTorrentInResultCatalog()
        }
    }

    private fun searchTorrentInResultCatalogWithPagination(maxPage: Int) {

        val search = activeQuery.deleteShortWords()

        for (n in 1..maxPage) {

            val document = Jsoup.connect("${site.url}page/$n/")
                .data("s", search)
                .get()

            if (document is Document)
                document.searchTorrentInResultCatalog()

            if (isFound) break
        }
    }


    private fun Document.searchTorrentInResultCatalog() {

        val elements = this.select(CssQuery.CatalogItem.query)

        val hasElements = (elements is Elements).and(elements.isNotEmpty())

        if (hasElements)
            elements.searchItemInCatalogElements()
    }

    private fun Elements.searchItemInCatalogElements() {

        this.forEach { element ->

            val title = element?.selectFirst(CssQuery.CatalogItemTitleLink.query)

            val titleText = title?.text()?.trim()?.lowercase()!!

            val isItemMatched = (titleText == activeQuery).or(
                activeQuery.deleteShortWords().split(' ').any { word ->
                    titleText.contains(
                        word
                    )
                })

            if (isItemMatched) {

                val elTextYear = element.selectFirst(CssQuery.CatalogItemYear.query)?.text()
                val isValidElTextYear =
                    elTextYear?.isNotBlank() == true && NumberUtils.isParsable(elTextYear)
                var isItemYearMatched = false

                if (isValidElTextYear)
                    isItemYearMatched = elTextYear?.toInt() == query.year

                if (isItemYearMatched) {

                    val url = title.attr("href").trim()

                    if (url.isHttpUrl()) {

                        searchTorrentInItemDetailDocument(url)
                        isFound = true
                        return // break => item found
                    }
                }
            }
        }
    }

    private fun searchTorrentInItemDetailDocument(itemUrl: String) {

        val document = Jsoup.connect(itemUrl).get()

        document.let document@{ docHtml ->

            val elTorrentContent = docHtml.selectFirst(CssQuery.ItemDetailTorrentContent.query)

            elTorrentContent?.let elTorrent@{

                it.selectFirst(CssQuery.ItemDetailTorrentLink.query)?.let { a ->

                    val torrentUrl = a.attr("href")

                    if (torrentUrl.isMagnetUrl()) {

                        val elTextQuality =
                            it.selectFirst(CssQuery.ItemDetailTorrentQuality.query)?.text()

                        val elTextSize =
                            it.select(CssQuery.ItemDetailTorrentSize.query).last()?.text()
                        var tSize = elTextSize
                        if (tSize?.isNotBlank() == true && tSize.contains("GB").not())
                            tSize = tSize.plus(" GB")

                        val tTitle =
                            "$activeQuery.${query.year}.${elTextQuality?.replace(" ", ".")}"

                        result.add(
                            Torrent(
                                site = site,
                                magnet = torrentUrl,
                                title = tTitle,
                                quality = elTextQuality,
                                size = tSize,
                                language = "latino"
                            )
                        )
                    }
                }
            }
        }
    }


    /**
     *  CSS Queries to search in sites html-element.
     */
    private enum class CssQuery(val query: String) {

        CatalogItem("div.browse-content div.browse-movie-wrap"),
        CatalogItemTitleLink(".browse-movie-title"),
        CatalogItemYear(".browse-movie-year"),
        CatalogPagination("div.browse-content div.tsc_pagination"),
        CatalogPaginationItem("> .page-numbers:not(.next)"),

        ItemDetailTorrentContent("#downloadsmodallatino"),
        ItemDetailTorrentLink(".modal-torrent a.download-torrent"),
        ItemDetailTorrentSize(".modal-torrent .quality-size"),
        ItemDetailTorrentQuality(".modal-torrent .modal-quality"),
    }
}