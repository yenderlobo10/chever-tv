package io.chever.tv.ui.movies.common.presenter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.leanback.widget.BaseCardView
import coil.load
import io.chever.tv.R
import io.chever.tv.common.extension.Extensions.pxFromDp
import io.chever.tv.common.extension.Util
import io.chever.tv.common.models.MovieCardItem
import io.chever.tv.common.presenter.BaseCardPresenter

/**
 * TODO: document class
 */
class MovieCardItemPresenter(context: Context) :
    BaseCardPresenter<BaseCardView, MovieCardItem>(context) {

    private val movieCollections = Util.createMoviesCollectionsByRawJson(context)


    override fun onCreteView(): BaseCardView {

        return BaseCardView(context).apply {

            isFocusable = true

            // Add card view =>

            val cardViewContent =
                View.inflate(context, R.layout.item_movie_list_row, null)

            addView(cardViewContent)
        }
    }

    override fun onBindViewHolder(cardModel: MovieCardItem, cardView: BaseCardView) {

        // Setup backdrop =>
        setupMovieBackdropImage(cardModel, cardView)

        // Setup chips =>
        setupMovieChips(cardModel, cardView)

        // Setup title =>
        val tvMovieTitle = cardView.findViewById<TextView>(R.id.tvItemTitle)
        tvMovieTitle.text = cardModel.transTitle

        // Setup year =>
        val tvMovieYear = cardView.findViewById<TextView>(R.id.tvItemYear)
        tvMovieYear.text = cardModel.year.toString()
    }

    override fun onUnbindViewHolder(cardView: BaseCardView) {

        // Fix - remove movie tv-chips don't repeat.
        cardView.findViewById<LinearLayout>(R.id.contentItemChips)?.removeAllViews()
    }


    private fun setupMovieBackdropImage(item: MovieCardItem, view: BaseCardView) {

        val ivMovieBackground = view.findViewById<ImageView>(R.id.ivItemBackground)

        val placeholderDrawable =
            ContextCompat.getDrawable(context, R.drawable.logo_foreground)?.apply {
                alpha = 200
            }
                ?.toBitmap(COIL_PLACEHOLDER_SIZE, COIL_PLACEHOLDER_SIZE)
                ?.toDrawable(context.resources)

        // Setup & Load network image =>
        with(ivMovieBackground) {

            scaleType = ImageView.ScaleType.CENTER

            load(item.backdropUrl) {

                crossfade(true)
                placeholder(placeholderDrawable)
                error(placeholderDrawable)

                listener(

                    onError = { _, _ ->

                        with(this@with.layoutParams as ConstraintLayout.LayoutParams) {

                            ivMovieBackground.layoutParams = ConstraintLayout.LayoutParams(
                                this.width,
                                this.height
                            ).apply {

                                setPadding(
                                    0, 0, 0,
                                    COIL_PLACEHOLDER_MARGIN.pxFromDp(context)
                                )
                            }
                        }
                    },

                    onSuccess = { _, _ ->
                        this@with.scaleType = ImageView.ScaleType.FIT_XY
                    }
                )
            }
        }
    }

    private fun setupMovieChips(item: MovieCardItem, view: BaseCardView) {

        if (item.chips.isEmpty()) return

        val contentChips = view.findViewById<LinearLayout>(R.id.contentItemChips)
        val movieCollection = movieCollections.find { x -> x.id == item.collection.id }!!

        item.chips.forEachIndexed { i, chip ->

            // Create tv-chip
            val tvChip = TextView(
                context,
                null,
                0,
                R.style.MovieItemChip
            ).apply {

                // text-chip
                text = chip

                // margin
                if (i > 0) {

                    layoutParams = ViewGroup.MarginLayoutParams(
                        ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                        ViewGroup.MarginLayoutParams.WRAP_CONTENT
                    ).apply {

                        marginStart = CHIP_HORIZONTAL_MARGIN.pxFromDp(context)
                    }
                }

                // background
                val resColorName =
                    movieCollection.resChipsColorsId?.getOrNull(i)
                        ?: if (i > 0) R.color.labelGray.toString()
                        else R.color.labelRed.toString()

                var resColorId = context.resources.getIdentifier(
                    resColorName, "color", context.packageName
                ).takeIf { x -> x > 0 }

                resColorId =
                    resColorId ?: if (i > 0) R.color.labelGray else R.color.labelRed

                val chipColor = ContextCompat.getColor(context, resColorId)

                background = ContextCompat.getDrawable(
                    context,
                    R.drawable.label_chip_background
                ).apply {
                    this?.setTint(chipColor)
                }
            }

            // Add to container
            contentChips?.addView(tvChip)
        }
    }


    companion object {

        const val COIL_PLACEHOLDER_SIZE = 76
        const val COIL_PLACEHOLDER_MARGIN = 64
        const val CHIP_HORIZONTAL_MARGIN = 6
    }
}