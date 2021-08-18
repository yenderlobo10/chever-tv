package io.chever.tv.ui.movies.common.presenter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.leanback.widget.Presenter
import io.chever.tv.R
import io.chever.tv.api.themoviedb.domain.models.TMMovieDetail
import io.chever.tv.common.extension.DateTimeExtensions
import io.chever.tv.common.extension.DateTimeExtensions.onlyYear
import io.chever.tv.common.extension.DateTimeExtensions.toFormat
import io.chever.tv.common.extension.Extensions.gone
import io.chever.tv.common.extension.FSymbols
import io.chever.tv.common.extension.NumberExtensions.toCompactFormat
import io.chever.tv.common.extension.NumberExtensions.toFormatDecimalPercent

/**
 * TODO: document class
 */
class MovieDetailOverviewRowPresenter : Presenter() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {

        context = parent?.context!!

        val view = View.inflate(context, R.layout.movie_detail_description, null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {

        val mcViewHolder = MovieDetailViewHolder(viewHolder?.view)
        val mcItem = item as TMMovieDetail

        // Title & Year
        val movieTitle = mcItem.title;
        val movieYear = mcItem.releaseDate?.onlyYear()
        val titleHtml =
            context.getString(R.string.movie_detail_title_year_format, movieTitle, movieYear)
        mcViewHolder.tvTitle?.text =
            HtmlCompat.fromHtml(titleHtml, HtmlCompat.FROM_HTML_MODE_LEGACY)


        // Votes
        mcViewHolder.tvVoteAverage?.text = mcItem.voteAverage.toFormatDecimalPercent()
        mcViewHolder.tvVoteCount?.text = context.getString(
            R.string.movie_detail_count_votes_label,
            mcItem.voteCount.toCompactFormat()
        )

        // Released date
        mcViewHolder.tvReleaseDate?.text =
            mcItem.releaseDate?.toFormat(DateTimeExtensions.Pattern.DateTwo)
        // Runtime
        mcViewHolder.tvRuntime?.text = mcItem.runtime?.minutesToHoursFormat()

        // Genres
        mcViewHolder.tvGenres?.text =
            mcItem.genres.joinToString(FSymbols.middleDot) { x -> x.name }


        // Tagline
        with(mcItem.tagline) {
            if (isNullOrBlank())
                mcViewHolder.tvTagline?.gone()
            else
                mcViewHolder.tvTagline?.text = this
        }

        // Overview
        with(mcItem.overview) {
            if (isNullOrBlank())
                mcViewHolder.tvOverview?.gone()
            else
                mcViewHolder.tvOverview?.text = this
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
        // Not yet implemented
    }


    /// Convert movie runtime from minutes to hours/minutes.
    /// Ej. 162 => 2h 42m
    /// TODO: check possible extension
    private fun Int.minutesToHoursFormat(): String {

        return try {

            val hours = (this / 60)
            val minutes = (this % 60)

            "${hours}h ${minutes}m"

        } catch (ex: Exception) {

            ex.printStackTrace()
            "$this ?"
        }
    }


    /**
     * TODO: document inner class
     */
    inner class MovieDetailViewHolder(view: View?) {

        val tvTitle = view?.findViewById<TextView>(R.id.tvTitleYear)
        val tvVoteAverage = view?.findViewById<TextView>(R.id.tvVoteAverage)
        val tvVoteCount = view?.findViewById<TextView>(R.id.tvVoteCount)
        val tvReleaseDate = view?.findViewById<TextView>(R.id.tvReleaseDate)
        val tvRuntime = view?.findViewById<TextView>(R.id.tvRuntime)
        val tvGenres = view?.findViewById<TextView>(R.id.tvGenres)
        val tvTagline = view?.findViewById<TextView>(R.id.tvTagline)
        val tvOverview = view?.findViewById<TextView>(R.id.tvOverview)
    }
}