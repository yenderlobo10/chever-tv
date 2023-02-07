package io.chever.data.api.themoviedb.model

import com.squareup.moshi.Json
import java.io.Serializable

data class TMCollectionResponse<T>(

    val page: Int,
    val results: List<T>,

    @Json(name = "total_pages")
    val totalPages: Int,

    @Json(name = "total_results")
    val totalResults: Int

) : Serializable