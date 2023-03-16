package io.chever.domain.model.resource

import io.chever.domain.enums.ListCollection
import io.chever.domain.enums.TimeWindowEnum
import io.chever.shared.common.defaultPaginationLimit

data class ListCollectionParams(

    val collection: ListCollection,
    val period: TimeWindowEnum = TimeWindowEnum.Week,
    val limit: Int = defaultPaginationLimit
)