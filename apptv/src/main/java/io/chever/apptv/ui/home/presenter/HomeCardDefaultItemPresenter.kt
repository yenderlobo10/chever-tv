package io.chever.apptv.ui.home.presenter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.leanback.widget.BaseCardView
import coil.load
import io.chever.apptv.R
import io.chever.apptv.common.presenter.BaseCardPresenter
import io.chever.apptv.databinding.CardCollectionDefaultItemBinding
import io.chever.apptv.ui.home.model.MediaCardItem
import io.chever.apptv.ui.home.model.MediaItemMetadata
import io.chever.data.api.themoviedb.enums.TMImageSizeEnum
import io.chever.domain.model.media.MediaItemDetail
import io.chever.shared.enums.DateTimePattern
import io.chever.shared.extension.onlyYear
import io.chever.shared.extension.pxFromDp
import io.chever.shared.extension.toFormat

internal class HomeCardDefaultItemPresenter(
    context: Context
) : BaseCardPresenter<BaseCardView, MediaCardItem>(context) {

    override fun onCreteView(): BaseCardView {

        return BaseCardView(context).apply {

            isFocusable = true

            val cardViewLayout = View.inflate(
                context,
                R.layout.card_collection_default_item,
                null
            )

            addView(cardViewLayout)
        }
    }

    override fun onBindViewHolder(
        cardModel: MediaCardItem,
        cardView: BaseCardView
    ) {

        val binding = CardCollectionDefaultItemBinding.bind(
            cardView.findViewById(R.id.cardCollectionDefaultItem)
        )

        with(cardModel.mediaItem) {

            with(binding) {

                when (detail) {
                    is MediaItemDetail.Movie -> bindMovieItem(detail as MediaItemDetail.Movie)
                    is MediaItemDetail.Show -> bindShowItem(detail as MediaItemDetail.Show)
                    else -> throw NoSuchElementException()
                }

                this.bindMetadata(cardModel)
            }
        }
    }

    override fun onUnbindViewHolder(cardView: BaseCardView) {
        // Fix - remove items to avoid repetition.
        cardView.findViewById<LinearLayout>(R.id.lyWrapperChips)?.removeAllViews()
    }

    private fun CardCollectionDefaultItemBinding.bindMovieItem(
        movieDetail: MediaItemDetail.Movie
    ) {

        with(movieDetail.detail) {
            ivBackdrop.bindItemBackdrop(this.backdropPath)
            tvTitle.text = this.title
            tvYear.text = this.releaseAt?.onlyYear()
        }
    }

    private fun CardCollectionDefaultItemBinding.bindShowItem(
        showDetail: MediaItemDetail.Show
    ) {

        with(showDetail.detail) {
            ivBackdrop.bindItemBackdrop(this.backdropPath)
            tvTitle.text = this.name
            tvYear.text = this.firstAirDate?.onlyYear()
        }
    }

    private fun CardCollectionDefaultItemBinding.bindMetadata(
        cardModel: MediaCardItem
    ) {

        cardModel.collection.metadata.forEachIndexed { i, metadata ->

            val value = when (metadata.keyValue) {
                "anticipated" ->
                    cardModel.mediaItem.detail.getAnticipatedMetadataValue()
                else -> cardModel.mediaItem.metadata[metadata.keyValue]
            }

            value?.let {

                this.lyWrapperChips.addView(
                    createTvChip(
                        value = it,
                        data = metadata
                    ).apply {

                        layoutParams = ViewGroup.MarginLayoutParams(
                            ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                            ViewGroup.MarginLayoutParams.WRAP_CONTENT
                        ).apply {

                            if (i > 0)
                                marginStart = 6.pxFromDp(context)
                        }
                    }
                )
            }
        }
    }

    private fun MediaItemDetail.getAnticipatedMetadataValue(): String? = when (this) {
        is MediaItemDetail.Movie ->
            this.detail.releaseAt?.toFormat(DateTimePattern.DateThree)
        is MediaItemDetail.Show ->
            this.detail.firstAirDate?.toFormat(DateTimePattern.DateThree)
        else -> null
    }

    private fun createTvChip(
        value: Any,
        data: MediaItemMetadata
    ): TextView = TextView(
        context,
        null,
        0,
        R.style.MediaItemLabelChip
    ).apply {

        val textValue = "$value"

        data.resLabel?.let {
            text = context.getString(it, textValue)
        } ?: run {
            text = textValue
        }

        background = ContextCompat.getDrawable(
            context,
            R.drawable.bg_square_4x_shape
        ).apply {
            this?.setTint(
                ContextCompat.getColor(context, data.resColor)
            )
        }
    }

    private fun ImageView.bindItemBackdrop(
        backdropPath: String?
    ) {

        val posterUrl = TMImageSizeEnum.W500.createImageUrl(
            path = backdropPath
        )

        this.scaleType = ImageView.ScaleType.FIT_XY
        this.load(posterUrl) {
            crossfade(false)
            listener(
                onError = { _, _ ->
                    setImageResource(R.drawable.logo_foreground)
                    scaleType = ImageView.ScaleType.FIT_CENTER
                    setPadding(
                        96.pxFromDp(context),
                        64.pxFromDp(context),
                        96.pxFromDp(context),
                        116.pxFromDp(context)
                    )
                }
            )
        }
    }
}