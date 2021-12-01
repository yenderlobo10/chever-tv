package io.chever.tv.ui.movies.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.leanback.app.ProgressBarManager
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.*
import androidx.lifecycle.lifecycleScope
import io.chever.tv.R
import io.chever.tv.common.extension.Extensions.getResStringByName
import io.chever.tv.common.extension.Extensions.showShortToast
import io.chever.tv.common.extension.AppResult
import io.chever.tv.common.extension.Util
import io.chever.tv.ui.common.models.ErrorBuilder
import io.chever.tv.ui.common.models.MovieCardItem
import io.chever.tv.ui.common.models.RowHeaderItem
import io.chever.tv.ui.common.view.ErrorFragment
import io.chever.tv.ui.home.view.HomeFragment
import io.chever.tv.ui.movies.common.presenter.MovieCardItemPresenter
import io.chever.tv.ui.movies.viewModel.MoviesBrowseViewModel
import kotlinx.coroutines.flow.collect
import timber.log.Timber

/**
 * TODO: document class
 */
class MoviesBrowseFragment : RowsSupportFragment(), OnItemViewClickedListener {

    private val rowsCollectionsAdapter = ArrayObjectAdapter(ListRowPresenter())
    private val viewModel by activityViewModels<MoviesBrowseViewModel>()
    private lateinit var listCollections: Array<RowHeaderItem>
    private lateinit var progressManager: ProgressBarManager
    private lateinit var mFragmentManager: FragmentManager
    private lateinit var errorBuilder: ErrorBuilder


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {

            setupUI()

            createListMoviesByCollections()

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
            requireContext().showShortToast(R.string.app_unknown_error_one)
        }
    }


    private fun setupUI() {

        mFragmentManager = requireParentFragment().childFragmentManager
        progressManager = (requireParentFragment() as HomeFragment).progressBarManager

        initErrorBuilder()

        listCollections = Util.createMoviesCollectionsByRawJson(requireContext())

        adapter = rowsCollectionsAdapter

        onItemViewClickedListener = this
    }


    override fun onItemClicked(
        itemViewHolder: Presenter.ViewHolder?,
        item: Any?,
        rowViewHolder: RowPresenter.ViewHolder?,
        row: Row?
    ) {

        try {

            prepareAndStartMovieDetailActivity(
                item = (item as MovieCardItem)
            )

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
            requireContext().showShortToast(R.string.app_unknown_error_one)
        }

    }

    private fun prepareAndStartMovieDetailActivity(item: MovieCardItem) {

        val movieDetailIntent = Intent(
            requireContext(),
            MovieDetailActivity::class.java
        )

        movieDetailIntent.putExtra(
            MovieDetailActivity.PARAM_ITEM_MOVIE,
            item.detail
        )

        val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity()
        ).toBundle()

        requireActivity().startActivity(movieDetailIntent, bundle)
    }


    private fun createListMoviesByCollections() {

        lifecycleScope.launchWhenStarted {

            viewModel.findMoviesByCollections().collect { result ->

                when (result) {

                    is AppResult.Loading -> {

                        progressManager.show()
                    }

                    is AppResult.Success -> {

                        progressManager.hide()
                        createRowsByMoviesCollection(result.data)
                    }

                    else -> {

                        progressManager.hide()
                        ErrorFragment.create(errorBuilder).show()

                        result as AppResult.Error
                        Timber.e(result.exception, result.message)
                    }
                }
            }
        }
    }

    private fun createRowsByMoviesCollection(listMovieCardItems: List<MovieCardItem>) {

        listMovieCardItems.groupBy { x -> x.collection }.forEach { item ->

            val collection = listCollections.find { x -> x.id == item.key.id }

            if (collection !is RowHeaderItem)
                return@forEach

            val collectionTitle =
                requireContext().getResStringByName(collection.resTitleId) ?: collection.title
            val collectionHeader = HeaderItem(collection.id, collectionTitle)

            val collectionAdapter =
                ArrayObjectAdapter(MovieCardItemPresenter(requireContext()))

            item.value.forEach { movieCardItem ->

                collectionAdapter.add(movieCardItem)
            }

            rowsCollectionsAdapter.add(ListRow(collectionHeader, collectionAdapter))
        }
    }


    private fun initErrorBuilder() {

        errorBuilder = ErrorBuilder(
            fragmentManager = mFragmentManager,
            message = getString(R.string.movies_browse_error_message),
            imageResId = R.drawable.ic_warning_outlined,
            textButton = getString(R.string.app_button_reload),
            callbackButton = {
                // TODO: implement action reload
            }
        )
    }
}