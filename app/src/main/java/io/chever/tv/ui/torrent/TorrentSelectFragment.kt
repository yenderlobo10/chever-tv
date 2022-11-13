package io.chever.tv.ui.torrent

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.graphics.drawable.toBitmap
import androidx.leanback.app.GuidedStepSupportFragment
import androidx.leanback.widget.GuidanceStylist
import androidx.leanback.widget.GuidedAction
import coil.Coil
import coil.request.ImageRequest
import io.chever.tv.R
import io.chever.tv.api.themoviedb.domain.enums.TMImageSize
import io.chever.tv.api.themoviedb.domain.models.TMMovieDetail
import io.chever.tv.common.extension.DateTimeExtensions.year
import io.chever.tv.common.extension.Extensions.showShortToast
import io.chever.tv.common.extension.FSymbols
import io.chever.tv.common.extension.NumberExtensions.dpFromPx
import io.chever.tv.common.torrent.models.Torrent
import io.chever.tv.common.torrent.models.TorrentSearcherResult
import io.chever.tv.ui.common.enums.VideoType
import io.chever.tv.ui.common.models.PlayVideo
import io.chever.tv.ui.movies.view.MovieDetailActivity
import io.chever.tv.ui.movies.view.MovieDetailFragment
import io.chever.tv.ui.player.TorrentPlayerActivity
import timber.log.Timber

class TorrentSelectFragment : GuidedStepSupportFragment() {

    private lateinit var guidanceIconView: ImageView

    private lateinit var detailMovie: TMMovieDetail
    private lateinit var torrentResult: TorrentSearcherResult


    override fun onCreate(savedInstanceState: Bundle?) {

        try {

            initArguments()

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
            requireContext().showShortToast(R.string.app_unknown_error_three)
            requireActivity().finish()
        }

        super.onCreate(savedInstanceState)
    }

    private fun initArguments() {

        val params =
            requireActivity().intent?.extras
                ?: throw IllegalArgumentException()

        detailMovie =
            params.getSerializable(TorrentSelectActivity.PARAM_MOVIE_DETAIL) as TMMovieDetail
        torrentResult =
            params.getSerializable(TorrentSelectActivity.PARAM_TORRENT_LIST) as TorrentSearcherResult
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        guidanceIconView = view.findViewById(androidx.leanback.R.id.guidance_icon)
        guidanceIconView.transitionName = MovieDetailActivity.TRANSITION_SHARED_ELEMENT

        setupGuidanceIconWithMoviePoster()
    }

    override fun onProvideTheme(): Int = R.style.Theme_Chever_TorrentSelect

    override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {

        val description =
            if (detailMovie.overview?.isNotBlank()!!) detailMovie.overview
            else detailMovie.tagline

        // TODO: add default icon
        return GuidanceStylist.Guidance(
            detailMovie.title,
            description,
            null,
            ContextCompat.getDrawable(requireContext(), R.drawable.g_background_primary)
        )
    }


    override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {

        torrentResult.listTorrent.forEachIndexed { i, torrent ->

            actions.add(
                createGuidedActionTorrent(
                    torrent = torrent,
                    index = i
                )
            )
        }
    }

    override fun onGuidedActionClicked(action: GuidedAction?) {

        try {

            startPlayTorrentByItemSelected(action)

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
            requireActivity().showShortToast(R.string.app_unknown_error_one)
        }

    }

    private fun startPlayTorrentByItemSelected(action: GuidedAction?) {

        val torrent = torrentResult.listTorrent.find { x -> x.id == action?.id }

        val playVideo = PlayVideo(
            title = detailMovie.title,
            year = detailMovie.releaseDate?.year() ?: 0,
            tmdbId = detailMovie.id,
            type = VideoType.Movie,
            torrent = torrent!!,
            description = detailMovie.tagline ?: detailMovie.overview?.take(90),
            backdropUrl = TMImageSize.Original.createImageUrl(
                requireContext(),
                detailMovie.backdropPath
            )
        )

        Intent(requireContext(), TorrentPlayerActivity::class.java).apply {

            putExtra(TorrentPlayerActivity.PARAM_PLAY_VIDEO, playVideo)

            startActivity(this)
        }
    }


    private fun setupGuidanceIconWithMoviePoster() {

        val thumbWidth = MovieDetailFragment.DETAIL_THUMB_WIDTH.dpFromPx(requireContext())
        val thumbHeight = MovieDetailFragment.DETAIL_THUMB_HEIGHT.dpFromPx(requireContext())

        val imageUrl = TMImageSize.W400.createImageUrl(
            requireContext(),
            detailMovie.posterPath
        )

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

                    guidanceIconView.setImageDrawable(roundedImageFromResult)
                },
                onError = { error ->

                    guidanceIconView.setImageDrawable(error)
                }
            )
            .build()

        Coil.imageLoader(requireContext()).enqueue(request)
    }

    private fun createGuidedActionTorrent(torrent: Torrent, index: Int): GuidedAction {

        val resQualityIcon = getResIconByQuality(torrent.quality)

        val flagSymbol = getFlagSymbolByLanguage(torrent.language)

        var title = "Opción ${index + 1}"
        torrent.title?.let {
            title = "$flagSymbol $it"
        }

        var description = ""


        if (torrent.site.name.isNotBlank())
            description += "${FSymbols.web} ${torrent.site.name}"

        if (torrent.size?.isNotBlank() == true)
            description += "  ${FSymbols.disk} ${torrent.size}"

        if (torrent.downloads?.isNotBlank() == true)
            description += "  ${FSymbols.downArrow} ${torrent.downloads}"


        return GuidedAction.Builder(requireActivity())
            .id(torrent.id)
            .title(title)
            .description(description)
            .icon(resQualityIcon)
            .build()
    }

    private fun getResIconByQuality(qa: String?): Int {

        if (qa == null) return R.drawable.ic_sd

        return when {

            qa.contains("1080") -> R.drawable.ic_1080
            qa.contains("720") -> R.drawable.ic_720
            qa.contains("480") -> R.drawable.ic_480
            qa.contains("4k", true) -> R.drawable.ic_4k
            qa.contains("hd", true) -> R.drawable.ic_hd
            qa.contains("sd", true) -> R.drawable.ic_sd

            else -> R.drawable.ic_sd
        }
    }

    private fun getFlagSymbolByLanguage(language: String?): String {

        if (language == null) return "???"

        val lg = language.lowercase().trim()

        return when {

            arrayOf("latino", "mexico").any { x -> lg.contains(x) } ->
                FSymbols.flagMexico

            arrayOf("español", "spanish").any { x -> lg.contains(x) } ->
                FSymbols.flagSpain

            else -> FSymbols.flagEnglish
        }
    }


    companion object {
    }
}