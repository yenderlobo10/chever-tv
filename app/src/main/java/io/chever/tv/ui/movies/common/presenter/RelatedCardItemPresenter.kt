package io.chever.tv.ui.movies.common.presenter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.leanback.widget.BaseCardView
import coil.load
import coil.transform.RoundedCornersTransformation
import io.chever.tv.R
import io.chever.tv.api.themoviedb.domain.enums.TMImageSize
import io.chever.tv.common.extension.DateTimeExtensions
import io.chever.tv.common.extension.DateTimeExtensions.toFormat
import io.chever.tv.common.extension.NumberExtensions.dpFromPx
import io.chever.tv.common.extension.NumberExtensions.toFormatDecimalPercent
import io.chever.tv.ui.common.models.RelatedCardItem
import io.chever.tv.ui.common.presenter.BaseCardPresenter

class RelatedCardItemPresenter(context: Context) :
    BaseCardPresenter<BaseCardView, RelatedCardItem>(context) {

    override fun onCreteView(): BaseCardView {

        return BaseCardView(context).apply {

            isFocusable = true

            // Add card-view
            val cardViewContent = View.inflate(context, R.layout.item_related_card, null)
            addView(cardViewContent)
        }
    }

    override fun onBindViewHolder(cardModel: RelatedCardItem, cardView: BaseCardView) {

        setupBackdropImage(cardModel, cardView)

        cardView.findViewById<TextView>(R.id.tvReleaseDate).text =
            cardModel.releasedDate?.toFormat(DateTimeExtensions.Pattern.DateThree)

        cardView.findViewById<TextView>(R.id.tvVoteAverage).text =
            cardModel.voteAverage.toFormatDecimalPercent()
    }


    private fun setupBackdropImage(cardModel: RelatedCardItem, cardView: BaseCardView) {

        val ivPosterImage = cardView.findViewById<ImageView>(R.id.ivPosterImage)
        val urlImage = TMImageSize.W500.createImageUrl(context, cardModel.posterPath)

        ivPosterImage.load(urlImage) {

            crossfade(true)
            placeholder(R.drawable.g_background_primary)
            error(R.drawable.g_background_primary)
            transformations(
                RoundedCornersTransformation(
                    bottomRight = POSTER_IMAGE_CORNER_SIZE.dpFromPx(context).toFloat(),
                    bottomLeft = POSTER_IMAGE_CORNER_SIZE.dpFromPx(context).toFloat(),
                )
            )
        }
    }


    companion object {

        private const val POSTER_IMAGE_CORNER_SIZE = 12
    }
}