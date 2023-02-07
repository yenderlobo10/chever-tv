package io.chever.domain.repository

import io.chever.domain.enums.MediaTypeEnum
import io.chever.domain.enums.TimeWindowEnum
import io.chever.domain.model.collection.MediaItem
import io.chever.domain.model.resource.AppFailure
import io.chever.domain.model.resource.AppResult

interface CollectionRepository {

    suspend fun trending(
        timeWindow: TimeWindowEnum,
        mediaType: MediaTypeEnum = MediaTypeEnum.All
    ): AppResult<AppFailure, List<MediaItem>>
}