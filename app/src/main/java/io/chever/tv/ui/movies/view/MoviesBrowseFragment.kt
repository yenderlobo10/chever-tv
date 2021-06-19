package io.chever.tv.ui.movies.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.leanback.app.ProgressBarManager
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.*
import androidx.lifecycle.lifecycleScope
import com.orhanobut.logger.Logger
import io.chever.tv.R
import io.chever.tv.common.extension.Extensions.getResStringByName
import io.chever.tv.common.extension.Result
import io.chever.tv.common.extension.Util
import io.chever.tv.common.models.ErrorBuilder
import io.chever.tv.common.models.MovieCardItem
import io.chever.tv.common.models.RowHeaderItem
import io.chever.tv.ui.common.ErrorFragment
import io.chever.tv.ui.home.view.HomeFragment
import io.chever.tv.ui.movies.common.presenter.MovieCardItemPresenter
import io.chever.tv.ui.movies.viewModel.MoviesBrowseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MoviesBrowseFragment : RowsSupportFragment() {

    private val mRowsAdapter = ArrayObjectAdapter(ListRowPresenter())
    private val viewModel by activityViewModels<MoviesBrowseViewModel>()
    private lateinit var listCollections: Array<RowHeaderItem>
    private lateinit var progressManager: ProgressBarManager
    private lateinit var mFragmentManager: FragmentManager
    private lateinit var errorBuilder: ErrorBuilder
    private lateinit var errorFragment: ErrorFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {

            //setupUI()
            // TODO: implement method

        } catch (ex: Exception) {

            Logger.e(ex, ex.message!!)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {

            setupUI()

            createListMoviesByCollections()

        } catch (ex: Exception) {

            Logger.e(ex, ex.message!!)
            // TODO: show error toast
        }
    }


    private fun setupUI() {

        mFragmentManager = requireParentFragment().childFragmentManager
        progressManager = (requireParentFragment() as HomeFragment).progressBarManager

        initErrorBuilder()

        listCollections = Util.createMoviesCollectionsByRawJson(requireContext())

        adapter = mRowsAdapter
    }


    private fun createListMoviesByCollections() {

        lifecycleScope.launchWhenStarted {

            viewModel.findMoviesByCollections().collect { result ->

                when (result) {

                    is Result.Loading -> {

                        progressManager.show()
                    }

                    is Result.Success -> {

                        progressManager.hide()
                        createRowsByMoviesCollection(result.data)
                    }

                    else -> {

                        // TODO: show error fragment
                        progressManager.hide()
                        println(":: ERROR ::")
                        println((result as Result.Error).toString())
                    }
                }
            }
        }
    }

    private fun createRowsByMoviesCollection(listMovieCardItems: List<MovieCardItem>) {

        listMovieCardItems.groupBy { x -> x.collection }.forEach { item ->

            val collection = listCollections.find { x -> x.id == item.key.id }
            println("TEST => ${collection.toString()}")

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

            mRowsAdapter.add(ListRow(collectionHeader, collectionAdapter))
        }
    }


    private fun initErrorBuilder() {

        errorBuilder = ErrorBuilder(
            fragmentManager = mFragmentManager,
            message = getString(R.string.movies_browse_error_message),
            imageResId = R.drawable.ic_outlined_error,
            textButton = getString(R.string.app_button_reload),
            callbackButton = {
                // TODO: action reload
                lifecycleScope.launch {
                    delay(2000)
                    println("AFTER ... 2 SEG")
                }
            }
        )
    }
}