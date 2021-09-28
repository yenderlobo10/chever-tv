package io.chever.tv.common.torrent.models

/**
 * Define a search of torrent in provider site.
 *
 * @param queries List of terms to search in site.
 * @param duration Runtime in minutes.
 * @param year Year released.
 */
data class TorrentQuery(

    val queries: List<String>,
    val duration: Int,
    val year: Int,

    val idIMDB: String? = null,
    val idTMDB: Long? = 0,
)
