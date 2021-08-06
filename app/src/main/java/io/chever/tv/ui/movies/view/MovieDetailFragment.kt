package io.chever.tv.ui.movies.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.activityViewModels
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.app.DetailsSupportFragmentBackgroundController
import androidx.leanback.widget.*
import androidx.lifecycle.lifecycleScope
import coil.Coil
import coil.request.ImageRequest
import com.orhanobut.logger.Logger
import io.chever.tv.R
import io.chever.tv.api.themoviedb.domain.enums.TMImageSize
import io.chever.tv.api.themoviedb.domain.models.TMMovie
import io.chever.tv.api.themoviedb.domain.models.TMMovieDetail
import io.chever.tv.api.themoviedb.domain.models.TMPersonCast
import io.chever.tv.common.extension.Extensions.showLongToast
import io.chever.tv.common.extension.Extensions.showShortToast
import io.chever.tv.common.extension.NumberExtensions.dpFromPx
import io.chever.tv.common.extension.Result
import io.chever.tv.ui.common.models.PersonCardItem
import io.chever.tv.ui.common.models.RelatedCardItem
import io.chever.tv.ui.common.view.LoaderDialogFragment
import io.chever.tv.ui.movies.common.enums.MovieDetailAction
import io.chever.tv.ui.movies.common.presenter.MovieDetailOverviewRowPresenter
import io.chever.tv.ui.movies.common.presenter.PersonCardItemPresenter
import io.chever.tv.ui.movies.common.presenter.RelatedCardItemPresenter
import io.chever.tv.ui.movies.viewModel.MovieDetailViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * TODO: document fragment class
 */
class MovieDetailFragment : DetailsSupportFragment(), OnItemViewClickedListener,
    OnActionClickedListener {

    private val detailBackground = DetailsSupportFragmentBackgroundController(this)
    private val viewModel by activityViewModels<MovieDetailViewModel>()

    private lateinit var detailMovie: TMMovieDetail
    private lateinit var rowsPresenterSelector: ClassPresenterSelector
    private lateinit var rowsAdapter: ArrayObjectAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {

            initArguments()
            setupUI()
            setupMovieDetailExtraRows()

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
        }
    }


    private fun initArguments() {

        requireActivity().intent?.let { intent ->

            detailMovie = intent.getSerializableExtra(
                MovieDetailActivity.PARAM_ITEM_MOVIE
            ) as TMMovieDetail
        }
    }

    private fun setupUI() {

        rowsPresenterSelector = ClassPresenterSelector()
        rowsAdapter = ArrayObjectAdapter(rowsPresenterSelector)

        setupDetailOverviewRow()
        setupDetailOverviewRowPresenter()
        adapter = rowsAdapter

        setupDetailBackground()

        onItemViewClickedListener = this
    }


    private fun setupDetailOverviewRow() {

        val detailRow = DetailsOverviewRow(detailMovie)

        detailRow.isImageScaleUpAllowed = false
        setupDetailPosterImage(detailRow)

        setupDetailOverviewRowActions(detailRow)

        rowsAdapter.add(detailRow)
    }

    private fun setupDetailOverviewRowActions(detailRow: DetailsOverviewRow) {

        val actionsAdapter = ArrayObjectAdapter()

        // Play
        actionsAdapter.add(
            Action(
                MovieDetailAction.Play.id,
                getString(R.string.movie_detail_action_play_label),
                null,
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_play)
            )
        )

        // Trailer
        actionsAdapter.add(
            Action(
                MovieDetailAction.Play.id,
                getString(R.string.movie_detail_action_trailer_label)
            )
        )

        // My list
        actionsAdapter.add(
            Action(
                MovieDetailAction.Play.id,
                getString(R.string.movie_detail_action_add_list_label),
                null,
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_plus)
            )
        )

        detailRow.actionsAdapter = actionsAdapter
    }

    private fun setupDetailPosterImage(detailRow: DetailsOverviewRow) {

        // TODO: add default poster (image)
        detailRow.imageDrawable = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.g_background_primary
        )

        val imageUrl = TMImageSize.W400.createImageUrl(
            requireContext(),
            detailMovie.posterPath
        )

        val thumbWidth = DETAIL_THUMB_WIDTH.dpFromPx(requireContext())
        val thumbHeight = DETAIL_THUMB_HEIGHT.dpFromPx(requireContext())

        // Load poster-image with coil
        val request = ImageRequest.Builder(requireContext())
            .data(imageUrl)
            .error(R.drawable.g_background_primary)
            .size(thumbWidth, thumbHeight)
            .target(
                onSuccess = { result ->

                    val roundedImageFromResult = RoundedBitmapDrawableFactory.create(
                        resources,
                        result.toBitmap(thumbWidth, thumbHeight)
                    ).apply {
                        cornerRadius = 24f
                    }

                    detailRow.imageDrawable = roundedImageFromResult
                    rowsAdapter.notifyArrayItemRangeChanged(0, rowsAdapter.size())
                },
                onError = { error ->

                    detailRow.imageDrawable =
                        error?.toBitmap(thumbWidth, thumbHeight)?.toDrawable(resources)
                }
            )
            .build()

        Coil.imageLoader(requireContext()).enqueue(request)
    }


    private fun setupDetailOverviewRowPresenter() {

        val detailPresenter = FullWidthDetailsOverviewRowPresenter(
            MovieDetailOverviewRowPresenter()
        )

        val sharedElementHelper = FullWidthDetailsOverviewSharedElementHelper().apply {
            setSharedElementEnterTransition(
                requireActivity(),
                MovieDetailActivity.TRANSITION_SHARED_ELEMENT
            )
        }
        detailPresenter.setListener(sharedElementHelper)
        detailPresenter.isParticipatingEntranceTransition = false

        detailPresenter.onActionClickedListener = this

        rowsPresenterSelector.addClassPresenter(
            DetailsOverviewRow::class.java,
            detailPresenter
        )
    }

    private fun setupDetailBackground() {

        detailBackground.enableParallax()

        val imageUrl = TMImageSize.Original.createImageUrl(
            requireContext(),
            detailMovie.backdropPath
        )

        // Load background-image with coil
        // TODO: add default background (image)
        val request = ImageRequest.Builder(requireContext())
            .data(imageUrl)
            .error(R.drawable.g_background_primary)
            .target(
                onSuccess = { result ->

                    detailBackground.coverBitmap = result.toBitmap()
                    detailBackground.enableParallax()
                    rowsAdapter.notifyArrayItemRangeChanged(0, rowsAdapter.size())
                },
                onError = { error ->

                    detailBackground.coverBitmap = error?.toBitmap()
                }
            )
            .build()

        Coil.imageLoader(requireContext()).enqueue(request)
    }


    private fun setupMovieDetailExtraRows() {

        lifecycleScope.launchWhenStarted {

            try {

                loadMovieCast()
                loadMovieRecommendations()

            } catch (ex: Exception) {

                Logger.e(ex.message!!, ex)
                requireContext().showShortToast(R.string.app_unknown_error_one)
            }
        }
    }

    // Setup movie cast

    private suspend fun loadMovieCast() {

        viewModel.findMovieCredits(detailMovie.id).collect { result ->

            if (result is Result.Success) {
                result.data?.cast?.let {
                    createMovieCastRow(it)
                }
            }
        }
    }

    private fun createMovieCastRow(cast: List<TMPersonCast>) {

        val rowCastAdapter = ArrayObjectAdapter(PersonCardItemPresenter(requireContext()))

        cast.take(MAX_CAST_ITEMS_SHOW).forEach { item ->

            rowCastAdapter.add(
                PersonCardItem(
                    id = item.id,
                    profilePath = item.profilePath,
                    name = item.name,
                    character = item.character
                )
            )
        }

        val rowCastHeader = HeaderItem(0, getString(R.string.movie_detail_cast_title))

        rowsAdapter.add(ListRow(rowCastHeader, rowCastAdapter))
        rowsPresenterSelector.addClassPresenter(ListRow::class.java, ListRowPresenter())
    }

    // Setup movie recommendations

    private suspend fun loadMovieRecommendations() {

        viewModel.findMovieRecommendations(detailMovie.id).collect { result ->

            if (result is Result.Success) {

                result.data?.results?.let {
                    createMovieRecommendationsRow(it)
                }
            }
        }
    }

    private fun createMovieRecommendationsRow(listMovies: List<TMMovie>) {

        val rowRecommendationsAdapter =
            ArrayObjectAdapter(RelatedCardItemPresenter(requireContext()))

        listMovies.forEach { tmMovie ->

            rowRecommendationsAdapter.add(
                RelatedCardItem(
                    id = tmMovie.id,
                    title = tmMovie.title,
                    posterPath = tmMovie.posterPath,
                    releasedDate = tmMovie.releaseDate,
                    voteAverage = tmMovie.voteAverage
                )
            )
        }

        val rowRecommendationsHeader =
            HeaderItem(0, getString(R.string.movie_detail_related_title))

        rowsAdapter.add(ListRow(rowRecommendationsHeader, rowRecommendationsAdapter))
        rowsPresenterSelector.addClassPresenter(ListRow::class.java, ListRowPresenter())
    }


    // Listener actions implement

    override fun onActionClicked(action: Action?) {

        try {

            // TODO: only test
            requireContext().showShortToast(action?.label1!!)
            validateActionClicked(action.id)

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
            requireContext().showShortToast(R.string.app_unknown_error_one)
        }
    }

    private fun validateActionClicked(actionId: Long) {

        when (actionId) {

            MovieDetailAction.Play.id -> {
                // TODO: go to play movie
            }
            MovieDetailAction.Trailer.id -> {
                // TODO: go to play trailer
            }
            MovieDetailAction.MyList.id -> {
                // TODO: add to my list
            }
        }
    }

    // Listener row-items implement

    override fun onItemClicked(
        itemViewHolder: Presenter.ViewHolder?,
        item: Any?,
        rowViewHolder: RowPresenter.ViewHolder?,
        row: Row?
    ) {

        try {

            validateItemClicked(item, itemViewHolder)

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
            requireContext().showShortToast(R.string.app_unknown_error_one)
        }
    }


    private fun validateItemClicked(
        item: Any?,
        itemViewHolder: Presenter.ViewHolder?
    ) {

        when (item) {

            is PersonCardItem -> whenCastItemClicked(item)

            is RelatedCardItem -> whenRecommendationItemClicked(item, itemViewHolder)
        }
    }

    private fun whenCastItemClicked(item: PersonCardItem) {

        // TODO: implement person detail view
        requireContext().showLongToast("${item.name} es ${item.character}")
    }

    private fun whenRecommendationItemClicked(
        item: RelatedCardItem,
        itemViewHolder: Presenter.ViewHolder?
    ) {

        val dialogLoader = LoaderDialogFragment.create(requireActivity()).show()

        lifecycleScope.launch {

            viewModel.findMovieDetails(item.id).collect { result ->

                if (result !is Result.Loading) dialogLoader.dismiss()

                if (result is Result.Success)
                    goToItemRecommendationDetail(result.data, itemViewHolder)

                if (result is Result.Error)
                    requireContext().showShortToast(R.string.app_unknown_error_two)
            }
        }
    }


    private fun goToItemRecommendationDetail(
        itemDetail: TMMovieDetail,
        itemViewHolder: Presenter.ViewHolder?
    ) {

        val detailIntent = Intent(requireContext(), MovieDetailActivity::class.java).apply {

            putExtra(MovieDetailActivity.PARAM_ITEM_MOVIE, itemDetail)
        }

        val ivItemPosterImage =
            itemViewHolder?.view?.findViewById<ImageView>(R.id.ivPosterImage)

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(),
            ivItemPosterImage!!,
            MovieDetailActivity.TRANSITION_SHARED_ELEMENT
        )

        startActivity(detailIntent, options.toBundle())
    }


    companion object {

        private const val DETAIL_THUMB_WIDTH = 320
        private const val DETAIL_THUMB_HEIGHT = 420
        private const val MAX_CAST_ITEMS_SHOW = 20
    }
}