package io.chever.apptv.ui.media

import android.os.Bundle
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.app.DetailsSupportFragmentBackgroundController
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.ClassPresenterSelector
import androidx.leanback.widget.DetailsOverviewRow
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.leanback.widget.RowPresenter
import coil.Coil
import coil.request.ImageRequest
import io.chever.apptv.R
import io.chever.apptv.ui.media.component.DetailCastRow
import io.chever.apptv.ui.media.component.DetailOverviewRow
import io.chever.apptv.ui.media.component.DetailRecommendationsRow
import io.chever.apptv.ui.media.model.MediaDetailExtraRow
import io.chever.apptv.ui.media.model.MediaDetailOverviewRow
import io.chever.data.api.themoviedb.enums.TMImageSizeEnum
import io.chever.domain.model.media.MediaItem
import io.chever.domain.model.media.MediaItemDetail
import io.chever.shared.extension.showLongToast
import io.chever.shared.observability.AppLogger

abstract class MediaDetailFragment<T : MediaItemDetail>(
    override val mediaItem: MediaItem
) : DetailsSupportFragment(), MediaDetailOverviewRow<T>, MediaDetailExtraRow<T> {

    private lateinit var backgroundController: DetailsSupportFragmentBackgroundController
    private lateinit var rowsPresenterSelector: ClassPresenterSelector
    private lateinit var rowsAdapter: ArrayObjectAdapter


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        try {
            setupUI()
        } catch (ex: Exception) {
            whenSetupUIError(ex)
        }
    }


    open fun setupUI() {

        postponeEnterTransition()
        initProperties()

        DetailOverviewRow.createIn(this)
        this.adapter = this.rowsAdapter

        loadBackgroundImage()
        setupDetailExtraRows()
    }

    private fun initProperties() {

        this.backgroundController = DetailsSupportFragmentBackgroundController(this)
        this.rowsPresenterSelector = ClassPresenterSelector()
        this.rowsAdapter = ArrayObjectAdapter(this.rowsPresenterSelector)
    }

    private fun loadBackgroundImage() {

        val urlImage = TMImageSizeEnum.Original.createImageUrl(
            path = this.pathBackground
        )

        val request = ImageRequest.Builder(requireContext())
            .data(urlImage)
            .error(R.drawable.bg_default_backdrop)
            .target(
                onSuccess = {

                    with(this.backgroundController) {
                        enableParallax()
                        coverBitmap = it.toBitmap()
                    }
                    this.rowsAdapter.notifyItemRangeChanged(
                        0, rowsAdapter.size()
                    )
                    startPostponedEnterTransition()
                },
                onError = {

                    with(this.backgroundController) {
                        enableParallax()
                        coverBitmap = it?.toBitmap()
                    }
                }
            )
            .build()

        Coil.imageLoader(requireContext()).enqueue(request)
    }

    private fun setupDetailExtraRows() {

        DetailCastRow.tryCreateIn(this)
        DetailRecommendationsRow.tryCreateIn(this)
    }

    private fun whenSetupUIError(ex: Throwable) {

        AppLogger.error(ex)
        requireContext().showLongToast(R.string.app_unknown_error_two)
        requireActivity().finish()
    }


    override fun detailFragment(): MediaDetailFragment<T> = this

    override fun addOverviewRow(row: DetailsOverviewRow) {

        this.rowsAdapter.add(row)
    }

    override fun addOverviewPresenter(presenter: RowPresenter) {

        this.rowsPresenterSelector.addClassPresenter(
            DetailsOverviewRow::class.java,
            presenter
        )
    }

    override fun posterImageLoadedNotify() {

        this.rowsAdapter.notifyItemRangeChanged(
            0,
            rowsAdapter.size()
        )
    }

    override fun addDetailExtraRow(
        index: Int?,
        row: ListRow
    ) {

        index?.let {
            this.rowsAdapter.add(index, row)
        } ?: run {
            this.rowsAdapter.add(row)
        }

        this.rowsPresenterSelector.addClassPresenter(
            ListRow::class.java,
            ListRowPresenter()
        )
    }


    // TODO: document abstract properties
    abstract val mediaDetail: T
    abstract val pathBackground: String
    abstract val pathPoster: String
}