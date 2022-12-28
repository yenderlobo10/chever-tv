package io.chever.apptv.ui.home.presenter

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.core.view.setPadding
import androidx.leanback.widget.BaseCardView
import coil.load
import io.chever.apptv.R
import io.chever.apptv.common.presenter.BaseCardPresenter
import io.chever.apptv.databinding.ItemMediaCollectionRowBinding
import io.chever.apptv.ui.home.model.MediaCardItem
import io.chever.data.api.themoviedb.enums.TMImageSizeEnum
import io.chever.shared.enums.DateTimePattern
import io.chever.shared.extension.pxFromDp
import io.chever.shared.extension.toFormat
import kotlin.math.roundToInt

class HomeMediaItemCardPresenter(
    context: Context
) : BaseCardPresenter<BaseCardView, MediaCardItem>(context) {

    override fun onCreteView(): BaseCardView {

        return BaseCardView(context).apply {

            isFocusable = true

            val cardViewLayout =
                View.inflate(context, R.layout.item_media_collection_row, null)

            addView(cardViewLayout)
        }
    }

    override fun onBindViewHolder(
        cardModel: MediaCardItem,
        cardView: BaseCardView
    ) {

        val binding = ItemMediaCollectionRowBinding.bind(
            cardView.findViewById(R.id.mediaCollectionItem)
        )

        with(cardModel.mediaItem) {
            with(binding) {

                ivPoster.bindItemPoster(posterPath = posterPath)
                bindItemRating(rating = rating)

                tvTitle.text = title
                tvRelease.text = release?.toFormat(DateTimePattern.DateFour)
            }
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

    private fun ItemMediaCollectionRowBinding.bindItemRating(
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