package io.chever.tv.common.presenter

import android.content.Context
import android.view.ViewGroup
import androidx.leanback.widget.BaseCardView
import androidx.leanback.widget.Presenter

@Suppress(
    "UNCHECKED_CAST",
    "MemberVisibilityCanBePrivate"
)
abstract class BaseCardPresenter<T : BaseCardView, E>(
    val context: Context,
) : Presenter() {


    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {

        val cardView = onCreteView()
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {

        onBindViewHolder(
            cardModel = item as E,
            cardView = viewHolder?.view as T
        )
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {

        onUnbindViewHolder(viewHolder?.view as T)
    }


    /**
     * Implement when a new card-view is created.
     *
     * @return The new card-view<T> created.
     */
    protected abstract fun onCreteView(): T


    /**
     * Implement to update card-view with data bound.
     *
     * @param cardModel The model of data bound on card.
     * @param cardView The view of card is bound.
     * @see Presenter.onBindViewHolder
     */
    protected abstract fun onBindViewHolder(cardModel: E, cardView: T)


    /**
     * Clean up card-view, override if necessary.
     * @param cardView The view of card is bound.
     * @see Presenter.onBindViewHolder
     */
    protected open fun onUnbindViewHolder(cardView: T) {
        // Default - Implement if necessary
    }
}