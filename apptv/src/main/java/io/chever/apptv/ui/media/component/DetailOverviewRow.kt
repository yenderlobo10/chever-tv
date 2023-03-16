package io.chever.apptv.ui.media.component

import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.leanback.widget.DetailsOverviewRow
import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter
import coil.Coil
import coil.request.ImageRequest
import io.chever.apptv.R
import io.chever.apptv.ui.media.model.MediaDetailOverviewRow
import io.chever.apptv.ui.media.presenter.DetailOverviewPresenter
import io.chever.data.api.themoviedb.enums.TMImageSizeEnum
import io.chever.domain.model.media.MediaItemDetail
import io.chever.shared.extension.dpFromPx

internal class DetailOverviewRow<T : MediaItemDetail> private constructor(
    private val detail: MediaDetailOverviewRow<T>
) {

    private val detailFragment by lazy { detail.detailFragment() }
    private val context by lazy { detail.detailFragment().requireContext() }
    private lateinit var actionsRow: DetailOverviewActionsRow


    init {
        createOverviewRowAdapter()
    }

    private fun createOverviewRowAdapter() {

        val detailRow = DetailsOverviewRow(
            this.detailFragment.mediaDetail
        ).apply {
            isImageScaleUpAllowed = false
            loadPosterImage(this)
        }

        detailRow.addActionsRow()
        this.detail.addOverviewRow(detailRow)
        addOverviewRowPresenter()
    }

    private fun DetailsOverviewRow.addActionsRow() {

        DetailOverviewActionsRow.with(
            detailRow = this,
            context = context
        ).apply {
            create()
            actionsRow = this
        }
    }

    private fun addOverviewRowPresenter() {

        val overviewPresenter = FullWidthDetailsOverviewRowPresenter(
            DetailOverviewPresenter()
        ).apply {
            // TODO: shared element transition
            isParticipatingEntranceTransition = false
        }

        overviewPresenter.onActionClickedListener = actionsRow
        this.detail.addOverviewPresenter(overviewPresenter)
    }

    private fun loadPosterImage(overviewRow: DetailsOverviewRow) {

        val resources = this.context.resources
        val imageWidth = 320.dpFromPx(this.context)
        val imageHeight = 420.dpFromPx(this.context)
        val urlImage = TMImageSizeEnum.W400.createImageUrl(
            path = this.detailFragment.pathPoster
        )

        val request = ImageRequest.Builder(this.context)
            .data(urlImage)
            .error(R.drawable.bg_default_poster)
            .placeholder(R.drawable.bg_default_poster)
            .size(imageWidth, imageHeight)
            .target(
                onSuccess = {

                    RoundedBitmapDrawableFactory.create(
                        resources,
                        it.toBitmap(imageWidth, imageHeight)
                    ).apply {
                        cornerRadius = 24f
                        overviewRow.imageDrawable = this
                    }

                    this.detail.posterImageLoadedNotify()
                },
                onError = {

                    overviewRow.imageDrawable =
                        it?.toBitmap(imageWidth, imageHeight)?.toDrawable(resources)
                }
            )
            .build()

        Coil.imageLoader(this.context).enqueue(request)
    }

    companion object {

        fun <T : MediaItemDetail> createIn(
            detail: MediaDetailOverviewRow<T>
        ) {
            DetailOverviewRow(detail)
        }
    }
}