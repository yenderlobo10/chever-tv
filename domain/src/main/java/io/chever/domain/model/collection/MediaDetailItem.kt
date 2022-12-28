package io.chever.domain.model.collection

data class MediaDetailItem<T>(

    val id: Long,
    val title: String,
    val year: Int?,
    val posterPath:String?,
    val backdropPath:String?,

    val detail:T
)
