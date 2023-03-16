package io.chever.apptv.ui.media.presenter

import android.view.View
import android.view.ViewGroup
import io.chever.apptv.R
import io.chever.apptv.common.presenter.BasePresenter
import io.chever.apptv.utils.FSymbols
import io.chever.apptv.databinding.MediaDetailOverviewBinding
import io.chever.domain.model.media.MediaItemDetail
import io.chever.domain.model.movie.MovieDetail
import io.chever.domain.model.show.ShowDetail
import io.chever.shared.enums.DateTimePattern
import io.chever.shared.extension.gone
import io.chever.shared.extension.onlyYear
import io.chever.shared.extension.toCompactFormat
import io.chever.shared.extension.toDecimalPercentFormat
import io.chever.shared.extension.toFormat
import io.chever.shared.observability.AppLogger
import java.util.Date

internal class DetailOverviewPresenter : BasePresenter<MediaItemDetail>() {

    override fun onCreteView(parent: ViewGroup): View = View.inflate(
        context,
        R.layout.media_detail_overview,
        null
    )

    override fun onBindViewHolder(
        view: View,
        viewModel: MediaItemDetail
    ) {

        val binding = MediaDetailOverviewBinding.bind(
            view.findViewById(R.id.mediaDetailOverview)
        )

        with(binding) {
            when (viewModel) {
                is MediaItemDetail.Movie -> bindMovie(viewModel.detail)
                is MediaItemDetail.Show -> bindShow(viewModel.detail)
                else -> throw NoSuchElementException()
            }
        }
    }

    private fun MediaDetailOverviewBinding.bindMovie(
        detail: MovieDetail
    ) {

        with(detail) {
            this@bindMovie.bindDetails(
                title = title,
                releaseAt = releaseAt,
                voteAverage = voteAverage,
                voteCount = voteCount,
                runtime = runtime,
                genres = genres,
                tagline = tagline,
                overview = overview,
                certification = certification
            )
        }
    }

    private fun MediaDetailOverviewBinding.bindShow(
        detail: ShowDetail
    ) {

        with(detail) {
            this@bindShow.bindDetails(
                title = name,
                releaseAt = firstAirDate,
                voteAverage = voteAverage,
                voteCount = voteCount,
                runtime = episodeRunTimeAverage,
                genres = genres,
                tagline = tagline,
                overview = overview,
                certification = certification
            )
        }
    }


    private fun MediaDetailOverviewBinding.bindDetails(
        title: String,
        releaseAt: Date?,
        voteAverage: Float,
        voteCount: Long,
        runtime: Int,
        genres: List<String>,
        tagline: String,
        overview: String,
        certification: String?
    ) {

        // Title & Year
        this.tvTitle.text = title
        this.tvYear.text = releaseAt?.onlyYear()

        // Votes
        this.tvVoteAverage.text = voteAverage.toDecimalPercentFormat()
        this.tvVoteCount.text = context.getString(
            R.string.media_detail_count_votes_label,
            voteCount.toCompactFormat()
        )
        // Release at
        this.tvReleaseAt.text = releaseAt?.toFormat(DateTimePattern.DateTwo)
        // Runtime
        this.tvRuntime.text = runtime.minutesToHoursFormat()

        // Certification
        if (certification.isNullOrBlank())
            this.tvCertification.gone()
        else
            this.tvCertification.text = certification

        //Genres
        this.tvGenres.text = genres.joinToString(FSymbols.middleDot)

        // Tagline
        if (tagline.isNotBlank())
            this.tvTagline.text = tagline
        else
            this.tvTagline.gone()

        // Overview
        if (overview.isNotBlank())
            this.tvOverview.text = overview
        else
            this.tvOverview.gone()

    }

    /// Convert movie runtime from minutes to hours/minutes.
    /// Ej. 162 => 2h 42m
    /// TODO: check possible extension
    private fun Int.minutesToHoursFormat(): String = try {

        val hours = (this / 60)
        val minutes = (this % 60)
        val minutesFormat = "${minutes}m"

        if (hours > 0) "${hours}h $minutesFormat"
        else minutesFormat

    } catch (ex: Exception) {

        AppLogger.warning(ex)
        "$this ?"
    }

}