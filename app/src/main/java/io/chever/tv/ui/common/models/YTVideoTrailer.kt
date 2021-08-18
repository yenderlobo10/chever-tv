package io.chever.tv.ui.common.models

import java.io.Serializable

/**
 * TODO: document data class
 */
data class YTVideoTrailer(

    val title: String,
    val description: String?,
    val videoSourceUrl: String,
    val audioSourceUrl: String,

    ) : Serializable
