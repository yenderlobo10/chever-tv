package io.chever.apptv.ui.media.component

import androidx.fragment.app.activityViewModels
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.lifecycle.lifecycleScope
import io.chever.apptv.R
import io.chever.apptv.model.ResourceState
import io.chever.apptv.ui.media.MediaDetailViewModel
import io.chever.apptv.ui.media.model.MediaDetailExtraRow
import io.chever.apptv.ui.media.model.PersonCastCardItem
import io.chever.apptv.ui.media.presenter.PersonCastItemPresenter
import io.chever.domain.model.detail.PersonCast
import io.chever.domain.model.media.MediaItemDetail
import kotlinx.coroutines.launch

internal class DetailCastRow<T : MediaItemDetail> private constructor(
    private val detail: MediaDetailExtraRow<T>
) {

    private val detailFragment by lazy { detail.detailFragment() }
    private val context by lazy { detailFragment.requireContext() }
    private val viewModel by detailFragment.activityViewModels<MediaDetailViewModel>()

    init {
        loadCast()
    }

    private fun loadCast() {

        detailFragment.lifecycleScope.launch {

            viewModel.cast.collect {

                if (it is ResourceState.Empty)
                    viewModel.loadCast(
                        id = detail.mediaItem.id,
                        mediaType = detail.mediaItem.type
                    )
                else if (it is ResourceState.Success)
                    createListRow(it.data)
            }
        }
    }

    private fun createListRow(
        list: List<PersonCast>
    ) {

        val adapter = ArrayObjectAdapter(
            PersonCastItemPresenter(context)
        ).apply {

            addAll(0, list.take(16).map {
                PersonCastCardItem(
                    id = it.id,
                    profilePath = it.profilePath,
                    name = it.name,
                    character = it.character
                )
            })
        }

        detail.addDetailExtraRow(
            index = 1,
            row = ListRow(createHeaderItemRow(), adapter)
        )
    }

    private fun createHeaderItemRow() =
        HeaderItem(0, context.getString(R.string.media_detail_cast_title))


    companion object {

        fun <T : MediaItemDetail> tryCreateIn(
            detail: MediaDetailExtraRow<T>
        ) {
            DetailCastRow(detail)
        }
    }
}