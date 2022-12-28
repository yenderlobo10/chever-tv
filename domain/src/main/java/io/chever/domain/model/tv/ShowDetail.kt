package io.chever.domain.model.tv

data class ShowDetail(

    val id: Long,
    val title: String,
    val year: Int?,
    val posterPath:String?,
    val backdropPath:String?
)
