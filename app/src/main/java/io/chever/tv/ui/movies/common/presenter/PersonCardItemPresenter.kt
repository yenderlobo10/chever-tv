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
import io.chever.tv.common.extension.NumberExtensions.dpFromPx
import io.chever.tv.ui.common.models.PersonCardItem
import io.chever.tv.ui.common.presenter.BaseCardPresenter

class PersonCardItemPresenter(context: Context) :
    BaseCardPresenter<BaseCardView, PersonCardItem>(context) {


    override fun onCreteView(): BaseCardView {

        return BaseCardView(context).apply {

            isFocusable = true

            // Add card-view
            val cardViewContent = View.inflate(context, R.layout.item_person_card, null)
            addView(cardViewContent)
        }
    }

    override fun onBindViewHolder(cardModel: PersonCardItem, cardView: BaseCardView) {

        // Profile image
        setupPersonProfileImage(cardModel, cardView)

        // Name & Character
        cardView.findViewById<TextView>(R.id.tvName)?.text = cardModel.name
        cardView.findViewById<TextView>(R.id.tvCharacter)?.text = cardModel.character
    }


    private fun setupPersonProfileImage(itemModel: PersonCardItem, cardView: BaseCardView) {

        val ivProfileImage = cardView.findViewById<ImageView>(R.id.ivProfileImage)
        val urlImage = TMImageSize.W400.createImageUrl(context, itemModel.profilePath)

        ivProfileImage.load(urlImage) {

            crossfade(true)
            placeholder(R.drawable.ic_user_outlined)
            error(R.drawable.ic_user_outlined)
            transformations(
                RoundedCornersTransformation(
                    bottomLeft = PROFILE_IMAGE_CORNER_SIZE.dpFromPx(context).toFloat(),
                    bottomRight = PROFILE_IMAGE_CORNER_SIZE.dpFromPx(context).toFloat()
                )
            )
        }
    }


    companion object {

        private const val PROFILE_IMAGE_CORNER_SIZE = 12
    }
}