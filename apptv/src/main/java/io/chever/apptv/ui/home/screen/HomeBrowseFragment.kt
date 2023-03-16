package io.chever.apptv.ui.home.screen

import androidx.fragment.app.activityViewModels
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.OnItemViewClickedListener
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.Row
import androidx.leanback.widget.RowPresenter
import androidx.lifecycle.lifecycleScope
import io.chever.apptv.R
import io.chever.apptv.common.view.RowsBrowseFragment
import io.chever.apptv.model.ResourceState
import io.chever.apptv.ui.home.enums.HomeCollection
import io.chever.apptv.ui.home.model.MediaCardItem
import io.chever.apptv.ui.home.presenter.HomeCardDefaultItemPresenter
import io.chever.apptv.ui.home.presenter.HomeCardTrendingItemPresenter
import io.chever.apptv.ui.main.MainViewModel
import io.chever.apptv.ui.media.MediaDetailActivity
import io.chever.shared.extension.showLongToast
import io.chever.shared.extension.showShortToast
import io.chever.shared.observability.AppLogger
import kotlinx.coroutines.launch

class HomeBrowseFragment : RowsBrowseFragment(), OnItemViewClickedListener {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private lateinit var listCollections: Array<HomeCollection>

    override fun setupUI() {

        this.listCollections = HomeCollection.values()
        loadMediaItemsByCollection()
        this.onItemViewClickedListener = this
    }

    private fun loadMediaItemsByCollection() {

        lifecycleScope.launch {

            mainViewModel.loadHomeCollections().collect {

                when (it) {

                    is ResourceState.Loading -> showProgress()

                    is ResourceState.Success -> {
                        hideProgress()
                        createRowsByCollection(it.data)
                    }

                    else -> {
                        hideProgress()
                        requireContext().showLongToast(R.string.app_unknown_error_two)
                        // TODO: show error dialog
                    }
                }
            }
        }
    }

    private fun createRowsByCollection(
        homeCollections: List<MediaCardItem>
    ) {

        homeCollections
            .groupBy { x -> x.collection }
            .forEach { item ->

                val listRow = ListRow(
                    createRowCollectionHeader(item.key),
                    createRowCollectionAdapter(
                        collection = item.key,
                        listItems = item.value
                    )
                )

                rowsCollectionsAdapter.add(listRow)
            }
    }

    private fun createRowCollectionHeader(
        collection: HomeCollection
    ): HeaderItem = HeaderItem(
        collection.id,
        getString(collection.resTitleId)
    )

    private fun createRowCollectionAdapter(
        collection: HomeCollection,
        listItems: List<MediaCardItem>
    ): ArrayObjectAdapter {

        val adapter = ArrayObjectAdapter(
            when (collection) {

                HomeCollection.TrendingDay,
                HomeCollection.TrendingWeek ->
                    HomeCardTrendingItemPresenter(requireContext())

                else ->
                    HomeCardDefaultItemPresenter(requireContext())
            }
        )

        adapter.addAll(0, listItems)

        return adapter
    }


    override fun onItemClicked(
        itemViewHolder: Presenter.ViewHolder?,
        item: Any?,
        rowViewHolder: RowPresenter.ViewHolder?,
        row: Row?
    ) {

        try {

            MediaDetailActivity.InstanceParams(
                mediaItem = (item as MediaCardItem).mediaItem
            ).run {
                launch(requireActivity())
            }

        } catch (ex: Exception) {

            AppLogger.error(ex)
            requireContext().showShortToast(R.string.app_unknown_error_one)
        }
    }
}