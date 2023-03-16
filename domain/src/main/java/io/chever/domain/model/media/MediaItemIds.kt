package io.chever.domain.model.media

import java.io.Serializable

data class MediaItemIds(

    val tmdb: Long? = null,
    val traktv: Long? = null,
    val slug: String? = null,
    val imdb: String? = null

) : Serializable
