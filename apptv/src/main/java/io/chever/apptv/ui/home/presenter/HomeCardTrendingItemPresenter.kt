package io.chever.apptv.ui.home.presenter

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.core.view.setPadding
import androidx.leanback.widget.BaseCardView
import coil.load
import io.chever.apptv.R
import io.chever.apptv.common.presenter.BaseCardPresenter
import io.chever.apptv.databinding.CardCollectionTrendingItemBinding
import io.chever.apptv.ui.home.model.MediaCardItem
import io.chever.data.api.themoviedb.enums.TMImageSizeEnum
import io.chever.domain.model.collection.MediaItemDetail
import io.chever.shared.enums.DateTimePattern
import io.chever.shared.extension.pxFromDp
import io.chever.shared.extension.toFormat
import kotlin.math.roundToInt

class HomeCardTrendingItemPresenter(
    context: Context
) : BaseCardPresenter<BaseCardView, MediaCardItem>(context) {

    override fun onCreteView(): BaseCardView {

        return BaseCardView(context).apply {

            isFocusable = true

            val cardViewLayout = View.inflate(
                context,
                R.layout.card_collection_trending_item,
                null
            )

            addView(cardViewLayout)
        }
    }

    override fun onBindViewHolder(
        cardModel: MediaCardItem,
        cardView: BaseCardView
    ) {

        val binding = CardCollectionTrendingItemBinding.bind(
            cardView.findViewById(R.id.cardCollectionTrendingItem)
        )

        with(cardModel.mediaItem) {

            with(binding) {

                when (detail) {
                    is MediaItemDetail.Movie -> bindMovieItem(detail as MediaItemDetail.Movie)
                    is MediaItemDetail.Show -> bindShowItem(detail as MediaItemDetail.Show)
                    else -> throw NoSuchElementException()
                }

                tvTitle.text = title
            }
        }
    }

    private fun CardCollectionTrendingItemBinding.bindMovieItem(
        movieDetail: MediaItemDetail.Movie
    ) {
        with(movieDetail.detail) {
            ivPoster.bindItemPoster(posterPath = posterPath)
            bindItemRating(rating = voteAverage)
            tvRelease.text = releaseAt?.toFormat(DateTimePattern.DateFour)
        }
    }

    private fun CardCollectionTrendingItemBinding.bindShowItem(
        showDetail: MediaItemDetail.Show
    ) {
        with(showDetail.detail) {
            ivPoster.bindItemPoster(posterPath = posterPath)
            bindItemRating(rating = voteAverage)
            tvRelease.text = firstAirDate?.toFormat(DateTimePattern.DateFour)
        }
    }

    private fun ImageView.bindItemPoster(
        posterPath: String?
    ) {

        val posterUrl = TMImageSizeEnum.W500.createImageUrl(
            path = posterPath
        )

        this.scaleType = ImageView.ScaleType.FIT_XY
        this.load(posterUrl) {
            crossfade(false)
            listener(
                onError = { _, _ ->
                    setBackgroundResource(R.drawable.g_background_primary)
                    setImageResource(R.drawable.logo_foreground)
                    scaleType = ImageView.ScaleType.FIT_CENTER
                    setPadding(96.pxFromDp(context))
                }
            )
        }
    }

    private fun CardCollectionTrendingItemBinding.bindItemRating(
        rating: Float
    ) {

        val ratingPercentValue = rating
            .div(10)
            .times(100)
            .roundToInt()

        pbRating.progress = ratingPercentValue.toFloat()
        tvRatingPercent.text = "$ratingPercentValue"
    }
}