package io.chever.apptv.ui.home.model

import androidx.annotation.ColorRes
import androidx.annotation.StringRes

data class MediaItemMetadata(
    val keyValue: String,
    @ColorRes val resColor: Int,
    @StringRes val resLabel: Int? = null
)
