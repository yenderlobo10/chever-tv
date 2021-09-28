package io.chever.tv.common.torrent.models

import java.io.Serializable

/**
 * TODO: document data class
 */
data class TorrentSearcherResult(

    val listTorrent: List<Torrent>,
    val hasError: Boolean,

    val notFound: Boolean = listTorrent.isEmpty(),

    ) : Serializable
