package io.chever.data.api.themoviedb.model

import com.squareup.moshi.Json

data class TMCollectionResponse<T>(

    val page: Int,
    val results: List<T>,

    @Json(name = "total_pages")
    val totalPages: Int,

    @Json(name = "total_results")
    val totalResults: Int
)