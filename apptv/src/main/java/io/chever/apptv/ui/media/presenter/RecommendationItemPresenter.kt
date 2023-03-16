package io.chever.apptv.ui.media.presenter

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.graphics.drawable.toBitmap
import androidx.leanback.widget.BaseCardView
import coil.load
import io.chever.apptv.R
import io.chever.apptv.common.presenter.BaseCardPresenter
import io.chever.apptv.databinding.CardRecommendationItemBinding
import io.chever.data.api.themoviedb.enums.TMImageSizeEnum
import io.chever.domain.enums.MediaTypeEnum
import io.chever.domain.model.media.MediaItem
import io.chever.domain.model.media.MediaItemDetail
import io.chever.shared.enums.DateTimePattern
import io.chever.shared.extension.toDecimalPercentFormat
import io.chever.shared.extension.toFormat
import java.util.Date

internal class RecommendationItemPresenter(context: Context) :
    BaseCardPresenter<BaseCardView, MediaItem>(context) {

    override fun onCreteView(): BaseCardView = BaseCardView(context).apply {

        isFocusable = true

        val cardViewLayout = View.inflate(
            context,
            R.layout.card_recommendation_item,
            null
        )

        addView(cardViewLayout)
    }

    override fun onBindViewHolder(
        cardModel: MediaItem,
        cardView: BaseCardView
    ) {
        val binding = CardRecommendationItemBinding.bind(
            cardView.findViewById(R.id.cardRecommendationItem)
        )

        with(binding) {
            when (cardModel.type) {
                MediaTypeEnum.Movie -> bindMovie(cardModel)
                MediaTypeEnum.Show -> bindShow(cardModel)
                else -> throw NoSuchElementException()
            }
        }
    }

    private fun CardRecommendationItemBinding.bindMovie(
        item: MediaItem
    ) {

        val movieDetail = item.detail as MediaItemDetail.Movie

        with(movieDetail.detail) {
            bindItem(
                posterPath = this.posterPath,
                releaseAt = this.releaseAt,
                voteAverage = this.voteAverage
            )
        }
    }

    private fun CardRecommendationItemBinding.bindShow(
        item: MediaItem
    ) {

        val showDetail = item.detail as MediaItemDetail.Show

        with(showDetail.detail) {
            bindItem(
                posterPath = this.posterPath,
                releaseAt = this.firstAirDate,
                voteAverage = this.voteAverage
            )
        }
    }

    private fun CardRecommendationItemBinding.bindItem(
        posterPath: String,
        releaseAt: Date?,
        voteAverage: Float
    ) {

        this.ivPosterImage.loadPosterImage(posterPath)
        this.tvReleaseDate.text = releaseAt?.toFormat(DateTimePattern.DateThree)
        this.tvVoteAverage.text = voteAverage.toDecimalPercentFormat()
    }

    private fun ImageView.loadPosterImage(
        path: String
    ) {
        val imageUrl = TMImageSizeEnum.W500.createImageUrl(path)

        this.load(imageUrl) {
            crossfade(false)
            error((R.drawable.bg_default_poster))
            listener(
                onSuccess = { _, result ->
                    RoundedBitmapDrawableFactory.create(
                        context.resources,
                        result.drawable.toBitmap()
                    ).apply {
                        cornerRadius = 12f
                        setImageDrawable(this)
                    }
                }
            )
        }
    }
}