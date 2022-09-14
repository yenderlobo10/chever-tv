package io.chever.tv.common.torrent.providers

import io.chever.tv.common.extension.StringExtensions.deleteShortWords
import io.chever.tv.common.extension.StringExtensions.isHttpUrl
import io.chever.tv.common.torrent.TorrentProvider
import io.chever.tv.common.torrent.enums.TorrentSite
import io.chever.tv.common.torrent.models.Torrent
import io.chever.tv.common.torrent.service.TorrentService.Companion.withBrowserHeader
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import timber.log.Timber

/**
 * TODO: document class
 */
class CineCalidadProvider : TorrentProvider(site = TorrentSite.CineCalidad) {

    private var isFound = false
    private var activeQuery = ""


    override fun startSearchTorrentInSite() {
        Timber.i("Torrent Search in :: ${site.name} by [${query.idIMDB}] ::")

        for (q in query.queries) {

            activeQuery = q.trim().lowercase()
            val search = q.deleteShortWords()

            val document = Jsoup.connect(site.url)
                .withBrowserHeader()
                .data(paramSearchName, search)
                .get()


            if (document is Document)
                document.checkIfSearchHasPagination()

            if (isFound) {
                isFound = false
                break
            }
        }

        // Finish search torrent in this provider
        successCallback.invoke(result)
    }


    // Search extension methods


    private fun Document.checkIfSearchHasPagination() {

        val elPagination = this.selectFirst(CssQuery.CatalogPagination.query)
        val hasPagination = elPagination is Element

        if (hasPagination) {

            // Get item pages text
            val pagesItemText =
                this.selectFirst(CssQuery.CatalogPaginationItemPages.query)?.text()

            // Get max-page from pages text
            pagesItemText?.let { pagesText ->

                val maxPage = pagesText.split(" ").last().toInt()
                searchInCatalogWithPagination(maxPage)
            }

        } else {

            this.searchCatalogInDocument()
        }
    }

    private fun searchInCatalogWithPagination(maxPage: Int) {

        val search = activeQuery.deleteShortWords()

        for (n in 1..maxPage) {

            val document = Jsoup.connect("${site.url}page/$n/")
                .withBrowserHeader()
                .data(paramSearchName, search)
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

            val title =
                element?.selectFirst(CssQuery.CatalogItemTitle.query) ?: return@forEach
            val titleText = title.text().trim().lowercase()

            val titleLink = element.selectFirst(CssQuery.CatalogItemTitleLink.query)

            val isItemMatched = (titleText == activeQuery).or(
                activeQuery.deleteShortWords().split(' ').any { word ->
                    titleText.contains(word)
                })

            if (isItemMatched) {

                titleLink?.attr("href")?.let { url ->

                    if (url.isHttpUrl()) {

                        searchTorrentsInItemDocument(url)
                        isFound = true
                        return // break => item found
                    }
                }
            }
        }
    }

    private fun searchTorrentsInItemDocument(itemUrl: String) {

        val document = Jsoup.connect(itemUrl).withBrowserHeader().get()

        document.let document@{ docHtml ->

            val downloadOptionList = docHtml.select(CssQuery.ItemDownloadOptionsList.query)
            if (downloadOptionList !is Elements) return

            downloadOptionList.forEach { option ->

                val isTorrent = option.text().lowercase().contains("torrent")

                if (isTorrent) option.createTorrentItemFromDownloadOptionList()
            }
        }
    }

    private fun Element.createTorrentItemFromDownloadOptionList() {

        val url = this.attr("href")
        val document = Jsoup.connect(url).withBrowserHeader().get()

        document.let { docHtml ->

            val magnetLink = docHtml.selectFirst(CssQuery.ItemOptionTorrentMagnet.query)
            val magnetHref = magnetLink?.attr("href")

            magnetHref?.let {

                val title = docHtml.selectFirst(CssQuery.ItemOptionTorrentTitle.query)
                val titleText = title?.text()

                val titleSplit = titleText?.split(' ')
                var quality: String? = null
                titleSplit?.forEachIndexed { index, s ->
                    if (s == "|") quality = titleSplit[index - 1]
                }

                result.add(
                    Torrent(
                        site = site,
                        magnet = magnetHref,
                        title = titleText,
                        quality = quality,
                        language = "latino",
                        downloads = "Recommended"
                    )
                )
            }
        }
    }


    /**
     *  CSS Queries to search in sites html-element.
     */
    private enum class CssQuery(val query: String) {

        CatalogPagination(".wp-pagenavi > a"),
        CatalogPaginationItemPages(".wp-pagenavi > .pages"),
        CatalogItem("#archive-content > .item"),
        CatalogItemTitle(".home_post_content > .in_title"),
        CatalogItemTitleLink(".hover_caption_caption > a"),
        ItemDownloadOptionsList("#panel_descarga ul.linklist > a"),
        ItemOptionTorrentTitle(".container > .titulo"),
        ItemOptionTorrentMagnet(".container > a.link"),
    }

    companion object {

        const val paramSearchName = "s"
    }
}