package io.chever.apptv.ui.media.component

import androidx.fragment.app.activityViewModels
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.lifecycle.lifecycleScope
import io.chever.apptv.R
import io.chever.apptv.model.ResourceState
import io.chever.apptv.ui.media.MediaDetailFragment
import io.chever.apptv.ui.media.MediaDetailViewModel
import io.chever.apptv.ui.media.model.MediaDetailExtraRow
import io.chever.apptv.ui.media.presenter.RecommendationItemPresenter
import io.chever.domain.model.media.MediaItem
import io.chever.domain.model.media.MediaItemDetail
import kotlinx.coroutines.launch

internal class DetailRecommendationsRow<T : MediaItemDetail> private constructor(
    private val detail: MediaDetailExtraRow<T>
) {

    private val detailFragment by lazy { detail.detailFragment() }
    private val context by lazy { detailFragment.requireContext() }
    private val viewModel by detailFragment.activityViewModels<MediaDetailViewModel>()

    init {
        loadRecommendations()
    }

    private fun loadRecommendations() {

        detailFragment.lifecycleScope.launch {

            viewModel.recommendations.collect {

                if (it is ResourceState.Empty)
                    viewModel.loadRecommendations(
                        id = detail.mediaItem.id,
                        mediaType = detail.mediaItem.type
                    )
                else if (it is ResourceState.Success)
                    createListRow(it.data)
            }
        }
    }

    private fun createListRow(
        listItems: List<MediaItem>
    ) {

        val adapter = ArrayObjectAdapter(
            RecommendationItemPresenter(context)
        ).apply {

            addAll(0, listItems)
        }

        detail.addDetailExtraRow(
            row = ListRow(createHeaderItemRow(), adapter)
        )
    }

    private fun createHeaderItemRow() =
        HeaderItem(0, context.getString(R.string.media_detail_related_title))


    companion object {

        fun <T : MediaItemDetail> tryCreateIn(
            detail: MediaDetailFragment<T>
        ) {
            DetailRecommendationsRow(detail)
        }
    }
}