package io.chever.apptv.ui.media.model

import androidx.leanback.widget.ListRow
import io.chever.domain.model.media.MediaItemDetail

internal interface MediaDetailExtraRow<T : MediaItemDetail> : MediaDetailRow<T> {

    fun addDetailExtraRow(
        index: Int? = null,
        row: ListRow
    )
}