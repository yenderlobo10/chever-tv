package io.chever.domain.repository

import io.chever.domain.enums.TimeWindowEnum
import io.chever.domain.model.detail.PersonCast
import io.chever.domain.model.media.MediaItem
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult
import io.chever.domain.model.show.ShowDetail

interface ShowRepository {

    suspend fun watched(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>>

    suspend fun recommended(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>>

    suspend fun collected(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>>

    suspend fun popular(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>>

    suspend fun anticipated(
        period: TimeWindowEnum,
        limit: Int
    ): AppResult<AppFailure, List<MediaItem>>

    suspend fun details(
        id: Long
    ): AppResult<AppFailure, ShowDetail>

    suspend fun credits(
        id: Long
    ): AppResult<AppFailure, List<PersonCast>>

    suspend fun recommendations(
        id: Long
    ): AppResult<AppFailure, List<MediaItem>>
}