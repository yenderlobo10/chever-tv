package io.chever.tv.ui.movies.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
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
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import coil.Coil
import coil.request.ImageRequest
import com.orhanobut.logger.Logger
import io.chever.tv.R
import io.chever.tv.api.themoviedb.domain.enums.TMImageSize
import io.chever.tv.api.themoviedb.domain.models.*
import io.chever.tv.common.extension.DateTimeExtensions.onlyYear
import io.chever.tv.common.extension.Extensions.showLongToast
import io.chever.tv.common.extension.Extensions.showShortToast
import io.chever.tv.common.extension.NumberExtensions.dpFromPx
import io.chever.tv.common.extension.Result
import io.chever.tv.common.extension.Util
import io.chever.tv.common.torrent.TorrentProviderSearcher
import io.chever.tv.common.torrent.models.TorrentQuery
import io.chever.tv.ui.common.models.PersonCardItem
import io.chever.tv.ui.common.models.RelatedCardItem
import io.chever.tv.ui.common.models.YTVideoTrailer
import io.chever.tv.ui.common.view.LoaderDialogFragment
import io.chever.tv.ui.movies.common.enums.MovieDetailAction
import io.chever.tv.ui.movies.common.presenter.MovieDetailOverviewRowPresenter
import io.chever.tv.ui.movies.common.presenter.PersonCardItemPresenter
import io.chever.tv.ui.movies.common.presenter.RelatedCardItemPresenter
import io.chever.tv.ui.movies.viewModel.MovieDetailViewModel
import io.chever.tv.ui.player.PlayerActivity
import io.chever.tv.ui.torrent.TorrentSelectActivity
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
    private lateinit var loaderDialog: LoaderDialogFragment

    private var alternativeTitles: List<TMMovieTitle> = arrayListOf()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {

            initArguments()
            setupUI()
            setupMovieDetailExtraRows()

            setupDetailBackground()

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

        postponeEnterTransition()

        rowsPresenterSelector = ClassPresenterSelector()
        rowsAdapter = ArrayObjectAdapter(rowsPresenterSelector)

        setupDetailOverviewRow()
        setupDetailOverviewRowPresenter()
        adapter = rowsAdapter

        //setupDetailBackground()

        onItemViewClickedListener = this

        loaderDialog = LoaderDialogFragment.create(requireActivity())
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
                MovieDetailAction.Trailer.id,
                getString(R.string.movie_detail_action_trailer_label)
            )
        )

        // My list
        actionsAdapter.add(
            Action(
                MovieDetailAction.MyList.id,
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

                    startPostponedEnterTransition()
                },
                onError = { error ->

                    detailBackground.coverBitmap = error?.toBitmap()

                    startPostponedEnterTransition()
                }
            )
            .build()

        Coil.imageLoader(requireContext()).enqueue(request)
    }


    private fun setupMovieDetailExtraRows() {

        lifecycleScope.launchWhenStarted {

            try {

                loadMovieRecommendations()
                loadMovieCast()
                loadAlternativeTitles()

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
                result.data.cast.let {
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

                result.data.results.let {
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

    // Setup movie alternative titles

    private suspend fun loadAlternativeTitles() {

        viewModel.findMovieAlternativeTitles(detailMovie.id).collect { result ->

            if (result is Result.Success)
                alternativeTitles = result.data
        }
    }


    // Listener actions implement

    override fun onActionClicked(action: Action?) {

        try {

            validateActionClicked(action?.id!!)

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
            requireContext().showShortToast(R.string.app_unknown_error_one)
        }
    }

    private fun validateActionClicked(actionId: Long) {

        when (actionId) {

            MovieDetailAction.Play.id -> findMovieTorrents()

            MovieDetailAction.Trailer.id -> findMovieTrailer()

            MovieDetailAction.MyList.id -> {
                // TODO: add to my list
                requireContext().showShortToast(":: PENDING ACTION ::")
            }
        }
    }

    // Trailer

    private fun findMovieTrailer() {

        lifecycleScope.launch {

            viewModel.findMovieTrailer(detailMovie.id).collect { result ->

                when (result) {

                    Result.Loading -> loaderDialog.show()

                    is Result.Success -> extractYouTubeVideoUrl(result.data)

                    is Result.Empty,
                    is Result.Error -> {

                        loaderDialog.dismiss()
                        requireContext().showLongToast("Trailer not found")
                    }
                }
            }
        }
    }

    private fun extractYouTubeVideoUrl(tmVideo: TMVideoResult) {

        // TODO: refactor this function
        lifecycleScope.launch {

            val ytExtractor = @SuppressLint("StaticFieldLeak")
            object : YouTubeExtractor(requireContext()) {
                override fun onExtractionComplete(
                    ytFiles: SparseArray<YtFile>?,
                    videoMeta: VideoMeta?
                ) {

                    val preferVideoKeyTags = arrayOf(
                        137, // mp4 | 1080p
                        136, // mp4 | 720p
                        248, // web | 1080p
                        247, // web | 720p
                        135, // mp4 | 480p
                        244, // web | 480p
                        134, // mp4 | 360p
                        243, // web | 360p
                        22,  // web | 720p
                        18,  // mp4 | 360p
                    )

                    val preferAudioKeyTags = arrayOf(
                        140, // m4a | 128b
                        251, // web | 160b
                        250, // web | 64b
                        249, // web | 48b
                    )

                    try {

                        var ytVideoFile: YtFile? = null

                        for (keyTag in preferVideoKeyTags) {

                            ytVideoFile = ytFiles?.get(keyTag)

                            if (ytVideoFile is YtFile) break
                        }

                        // ytFile video is found & find audio
                        if (ytVideoFile is YtFile) {

                            var ytAudioFile: YtFile? = null

                            for (keyTag in preferAudioKeyTags) {

                                ytAudioFile = ytFiles?.get(keyTag)

                                if (ytAudioFile is YtFile) break
                            }

                            if (ytAudioFile is YtFile) {

                                startPlayerToPlayTrailer(ytVideoFile, ytAudioFile)
                                return
                            }
                        }

                        requireContext().showLongToast("Trailer not found")

                    } catch (ex: Exception) {

                        requireContext().showLongToast("Trailer not extract")

                    } finally {

                        loaderDialog.dismiss()
                    }
                }
            }

            ytExtractor.extract(Util.createUrlYouTubeVideo(tmVideo.key))
        }
    }

    private fun startPlayerToPlayTrailer(ytVideoFile: YtFile, ytAudioFile: YtFile) {

        val videoTrailer = YTVideoTrailer(
            title = "Trailer: ${detailMovie.title}",
            description = detailMovie.tagline,
            videoSourceUrl = ytVideoFile.url,
            audioSourceUrl = ytAudioFile.url,
        )

        val playerIntent = Intent(requireContext(), PlayerActivity::class.java)

        playerIntent.putExtra(PlayerActivity.PARAM_VIDEO_ITEM, videoTrailer)

        startActivity(playerIntent)
    }

    // Play

    private fun findMovieTorrents() {

        // TODO: refactor this function
        loaderDialog.show()

        TorrentProviderSearcher.create()
            .startSearch(createTorrentQuery())
            .onSearchCompleted { result ->

                loaderDialog.dismiss()

                // Check found torrents
                if (result.notFound) {

                    lifecycleScope.launch {
                        requireContext().showLongToast("Not found torrents")
                    }
                    return@onSearchCompleted
                }

                // Go to select torrent
                lifecycleScope.launch {

                    Intent(requireContext(), TorrentSelectActivity::class.java).apply {

                        putExtra(TorrentSelectActivity.PARAM_MOVIE_DETAIL, detailMovie)
                        putExtra(TorrentSelectActivity.PARAM_TORRENT_LIST, result)

                        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            requireActivity(),
                            requireActivity().findViewById(androidx.leanback.R.id.details_overview_image),
                            MovieDetailActivity.TRANSITION_SHARED_ELEMENT
                        )

                        startActivity(this, options.toBundle())
                    }
                }
            }
    }


    private fun createTorrentQuery(): TorrentQuery {

        val titles = mutableListOf(detailMovie.title)

        val isDiffOriginalTitle = detailMovie.originalTitle != detailMovie.title

        if (isDiffOriginalTitle)
            titles.add(detailMovie.originalTitle)

        if (alternativeTitles.isNotEmpty())
            titles.addAll(alternativeTitles.map { x -> x.title })


        return TorrentQuery(
            queries = titles,
            duration = detailMovie.runtime!!,
            year = detailMovie.releaseDate?.onlyYear()?.toInt()!!,
            idIMDB = detailMovie.imdbId,
            idTMDB = detailMovie.id,
        )
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

        lifecycleScope.launch {

            viewModel.findMovieDetails(item.id).collect { result ->

                when (result) {

                    Result.Loading -> loaderDialog.show()

                    is Result.Success -> {

                        loaderDialog.dismiss()
                        goToItemRecommendationDetail(result.data, itemViewHolder)
                    }

                    else -> {

                        loaderDialog.dismiss()
                        requireContext().showShortToast(R.string.app_unknown_error_two)
                    }
                }
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

        const val DETAIL_THUMB_WIDTH = 320
        const val DETAIL_THUMB_HEIGHT = 420
        private const val MAX_CAST_ITEMS_SHOW = 20
    }
}