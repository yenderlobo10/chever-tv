package io.chever.apptv.ui.media.component

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.leanback.widget.Action
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.DetailsOverviewRow
import androidx.leanback.widget.OnActionClickedListener
import io.chever.apptv.ui.media.enums.MediaDetailAction
import io.chever.shared.observability.AppLogger

class DetailOverviewActionsRow private constructor(
    private val detailRow: DetailsOverviewRow,
    private val context: Context
) : OnActionClickedListener {

    fun create(): DetailOverviewActionsRow {
        try {
            createAdapter()
        } catch (ex: Exception) {

            AppLogger.error(ex)
        }
        return this
    }

    private fun createAdapter() {

        val adapter = ArrayObjectAdapter().apply {
            addActions()
        }

        this.detailRow.actionsAdapter = adapter
    }

    private fun ArrayObjectAdapter.addActions() {

        addAll(0, MediaDetailAction.values().map { action ->
            Action(
                action.id,
                context.getString(action.resTitle),
                action.resSubtitle?.let { context.getString(it) },
                action.resIcon?.let { createActionIconDrawable(it) }
            )
        })
    }

    private fun createActionIconDrawable(
        @DrawableRes resIcon: Int
    ): Drawable? = ContextCompat.getDrawable(context, resIcon)


    override fun onActionClicked(action: Action?) {
        // TODO("Not yet implemented")
    }


    companion object {

        fun with(
            detailRow: DetailsOverviewRow,
            context: Context
        ) = DetailOverviewActionsRow(detailRow, context)
    }
}