package io.chever.domain.model.detail

import io.chever.domain.enums.GenderEnum

/**
 * Model represent a person of cast of a movie/show.
 *
 * @param id
 * @param name
 * @param gender
 * ...
 */
data class PersonCast(

    val id: Long,
    val name: String,
    val character: String,
    val profilePath: String,
    val gender: GenderEnum = GenderEnum.Other,
    val popularity: Float = 0f,
    val department: String? = null,
    val originalName: String? = null,
    val castId: Int? = null,
    val creditId: String? = null
)
