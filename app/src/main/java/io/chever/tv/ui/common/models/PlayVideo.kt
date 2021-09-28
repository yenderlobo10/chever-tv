package io.chever.tv.ui.common.models

import io.chever.tv.common.torrent.models.Torrent
import io.chever.tv.ui.common.enums.VideoType
import java.io.Serializable

/**
 * TODO: document class
 */
data class PlayVideo(

    val title: String,
    val type: VideoType,
    val torrent: Torrent,
    val description: String?,
    val backdropUrl: String?,

    ) : Serializable
