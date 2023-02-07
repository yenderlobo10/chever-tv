package io.chever.apptv.ui.home.screen

import androidx.fragment.app.activityViewModels
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.lifecycle.lifecycleScope
import io.chever.apptv.R
import io.chever.apptv.common.view.AppRowsBrowseFragment
import io.chever.apptv.ui.home.enums.HomeCollection
import io.chever.apptv.ui.home.model.MediaCardItem
import io.chever.apptv.ui.home.presenter.HomeCardDefaultItemPresenter
import io.chever.apptv.ui.home.presenter.HomeCardTrendingItemPresenter
import io.chever.apptv.ui.main.MainState
import io.chever.apptv.ui.main.MainViewModel
import io.chever.shared.extension.showLongToast

class HomeBrowseFragment : AppRowsBrowseFragment() {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private lateinit var listCollections: Array<HomeCollection>

    override fun setupUI() {

        listCollections = HomeCollection.values()
        loadMediaItemsByCollection()
    }

    private fun loadMediaItemsByCollection() {

        lifecycleScope.launchWhenStarted {

            mainViewModel.loadHomeCollections().collect {

                when (it) {

                    is MainState.Loading -> showProgress()

                    is MainState.Success -> {
                        hideProgress()
                        createRowsByCollection(it)
                    }

                    // TODO: show error dialog
                    else -> {
                        hideProgress()
                        requireContext().showLongToast(R.string.app_unknown_error_two)
                    }
                }
            }
        }
    }

    private fun createRowsByCollection(
        state: MainState.Success
    ) {

        state.homeCollections
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
}