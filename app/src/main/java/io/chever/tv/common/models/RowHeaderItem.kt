package io.chever.tv.common.models

/**
 * TODO: document class
 */
data class RowHeaderItem(

    val id: Long,
    val title: String,
    val resTitleId: String,

    val resChipsColorsId: List<String>?,
    val resChipsStringId: List<String>?,
)
