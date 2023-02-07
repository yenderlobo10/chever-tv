package io.chever.data.api.themoviedb.model

data class TMResultsResponse<T>(

    val results: List<T>
)
