package io.chever.data.api.themoviedb.mapper.detail

import io.chever.data.api.themoviedb.model.TMCreditsResponse
import io.chever.data.api.themoviedb.model.detail.TMPersonCast
import io.chever.domain.enums.GenderEnum
import io.chever.domain.model.detail.PersonCast
import io.chever.shared.extension.emptyIfNull

/**
 * TODO: document
 */
fun TMCreditsResponse.mapToPersonCastList() = this.cast.map {
    it.mapToPersonCast()
}

/**
 * TODO: document
 */
fun TMPersonCast.mapToPersonCast() = PersonCast(
    id = this.id,
    name = this.name,
    character = this.character,
    profilePath = this.profilePath.emptyIfNull(),
    gender = GenderEnum.fromValue(this.gender),
    popularity = this.popularity,
    department = this.knownForDepartment,
    originalName = this.originalName,
    castId = this.castId,
    creditId = this.creditId
)