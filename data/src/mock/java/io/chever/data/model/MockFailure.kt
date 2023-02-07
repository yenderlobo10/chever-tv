package io.chever.data.model

import io.chever.domain.model.resource.AppFailure

data class MockFailure(
    val message: String
) : AppFailure.FeatureFailure()