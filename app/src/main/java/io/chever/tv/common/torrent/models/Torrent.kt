package io.chever.tv.common.torrent.models

import io.chever.tv.common.extension.Util
import io.chever.tv.common.torrent.enums.TorrentSite
import java.io.Serializable

/**
 * TODO: document data class
 */
data class Torrent(

    /**
     * Auto generate GUID.
     */
    val id: Long = Util.genRandomLongId(),
    /**
     * Site of torrent, one of [TorrentSite].
     */
    val site: TorrentSite,
    val magnet: String,
    var title: String? = null,
    var quality: String? = null,
    var language: String? = null,
    var size: String? = null,
    var downloads: Int = 0,

    ) : Serializable
