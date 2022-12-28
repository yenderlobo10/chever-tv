package io.chever.data.model

import io.chever.domain.model.result.AppFailure

data class MockFailure(
    val message: String
) : AppFailure.FeatureFailure()