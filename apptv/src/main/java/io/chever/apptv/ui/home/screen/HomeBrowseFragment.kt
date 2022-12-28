package io.chever.apptv.ui.home.screen

import androidx.fragment.app.activityViewModels
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.lifecycle.lifecycleScope
import io.chever.apptv.common.view.AppRowsBrowseFragment
import io.chever.apptv.ui.home.enums.HomeCollection
import io.chever.apptv.ui.home.model.MediaCardItem
import io.chever.apptv.ui.home.presenter.HomeMediaItemCardPresenter
import io.chever.apptv.ui.main.MainViewModel
import io.chever.apptv.ui.main.state.MainState

class HomeBrowseFragment : AppRowsBrowseFragment() {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private lateinit var listCollections: Array<HomeCollection>

    override fun setupUI() {

        listCollections = HomeCollection.values()
        loadMediaItemsByCollection()
    }

    private fun loadMediaItemsByCollection() {

        lifecycleScope.launchWhenStarted {

            mainViewModel.mainState.collect {

                when (it) {

                    is MainState.Success -> createRowsByCollection(it)

                    else -> throw IllegalStateException(it.toString())
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
                    createRowCollectionAdapter(item.value)
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
        listItems: List<MediaCardItem>
    ): ArrayObjectAdapter {

        val adapter = ArrayObjectAdapter(
            HomeMediaItemCardPresenter(requireContext())
        )

        adapter.addAll(0, listItems)

        return adapter
    }

}