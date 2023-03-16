package io.chever.apptv.common.presenter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.leanback.widget.Presenter
import io.chever.shared.observability.AppLogger

abstract class BasePresenter<T> : Presenter() {

    protected lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {

        parent?.let {

            this.context = parent.context
            return ViewHolder(onCreteView(it))

        } ?: throw IllegalArgumentException()
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(
        viewHolder: ViewHolder?,
        item: Any?
    ) {

        try {

            this.onBindViewHolder(
                view = viewHolder?.view!!,
                viewModel = item as T
            )

        } catch (ex: Exception) {
            AppLogger.error(ex)
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
        viewHolder?.let {
            this.onUnbindViewHolder(it.view)
        }
    }

    /**
     * Implement when a new view is created.
     *
     * @return The new [View] created.
     */
    protected abstract fun onCreteView(parent: ViewGroup): View

    /**
     * Implement to update view with data bound.
     *
     * @param view The view of card is bound.
     * @param viewModel The model of data bound on view.
     * @see Presenter.onBindViewHolder
     */
    protected abstract fun onBindViewHolder(view: View, viewModel: T)

    /**
     * Clean up view. *Override if necessary*.
     * @param view The view is bound.
     * @see Presenter.onBindViewHolder
     */
    protected open fun onUnbindViewHolder(view: View) {
        // Default - Implement if necessary
    }
}