package io.chever.apptv.ui.media.model

import androidx.leanback.widget.DetailsOverviewRow
import androidx.leanback.widget.RowPresenter
import io.chever.domain.model.media.MediaItemDetail

internal interface MediaDetailOverviewRow<T : MediaItemDetail> : MediaDetailRow<T> {

    fun addOverviewRow(row: DetailsOverviewRow)

    fun addOverviewPresenter(presenter: RowPresenter)

    fun posterImageLoadedNotify()
}