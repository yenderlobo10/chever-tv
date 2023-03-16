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
import io.chever.apptv.databinding.CardPersonCastItemBinding
import io.chever.apptv.ui.media.model.PersonCastCardItem
import io.chever.data.api.themoviedb.enums.TMImageSizeEnum
import io.chever.shared.extension.pxFromDp

internal class PersonCastItemPresenter(context: Context) :
    BaseCardPresenter<BaseCardView, PersonCastCardItem>(context) {

    override fun onCreteView(): BaseCardView = BaseCardView(context).apply {

        isFocusable = true

        val cardViewLayout = View.inflate(
            context,
            R.layout.card_person_cast_item,
            null
        )

        addView(cardViewLayout)
    }

    override fun onBindViewHolder(
        cardModel: PersonCastCardItem,
        cardView: BaseCardView
    ) {

        val binding = CardPersonCastItemBinding.bind(
            cardView.findViewById(R.id.cardPersonCastItem)
        )

        with(binding) {

            ivProfileImage.loadProfileImage(
                path = cardModel.profilePath
            )
            tvName.text = cardModel.name
            tvCharacter.text = cardModel.character
        }
    }

    private fun ImageView.loadProfileImage(
        path: String
    ) {
        val imageUrl = TMImageSizeEnum.W400.createImageUrl(path)

        scaleType = ImageView.ScaleType.FIT_XY
        this.load(imageUrl) {
            crossfade(false)
            error((R.drawable.logo_foreground))
            listener(
                onSuccess = { _, result ->
                    RoundedBitmapDrawableFactory.create(
                        context.resources,
                        result.drawable.toBitmap()
                    ).apply {
                        cornerRadius = 12f
                        setImageDrawable(this)
                    }
                },
                onError = { _, _ ->
                    scaleType = ImageView.ScaleType.FIT_CENTER
                    setPadding(
                        36.pxFromDp(context),
                        48.pxFromDp(context),
                        36.pxFromDp(context),
                        48.pxFromDp(context)
                    )
                }
            )
        }
    }
}