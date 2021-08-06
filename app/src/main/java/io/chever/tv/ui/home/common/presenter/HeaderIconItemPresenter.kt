package io.chever.tv.ui.home.common.presenter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.PageRow
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.RowHeaderPresenter
import io.chever.tv.R
import io.chever.tv.common.extension.StringExtensions.capitalize

/**
 * TODO: document class
 */
class HeaderIconItemPresenter(
    private val resTitleId: Int,
    private val resIconId: Int,
) : RowHeaderPresenter() {


    override fun onCreateViewHolder(parent: ViewGroup?): Presenter.ViewHolder {

        val inflater =
            parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.header_icon_item, null)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: Presenter.ViewHolder?, item: Any?) {

        var headerItem: HeaderItem? = null

        if (item is PageRow)
            headerItem = item.headerItem as HeaderItem

        headerItem?.let {

            setupItemLayout(viewHolder!!, it)
        }
    }


    private fun setupItemLayout(viewHolder: Presenter.ViewHolder, item: HeaderItem) {

        val rootView = viewHolder.view!!
        rootView.isFocusable = true

        // Icon
        rootView.findViewById<ImageView>(R.id.header_icon)?.let {

            val iconResId =
                if (resIconId > 0) resIconId
                else R.drawable.ic_android

            val iconDrawable = ContextCompat.getDrawable(rootView.context, iconResId)
            it.setImageDrawable(iconDrawable)
        }

        // Title
        rootView.findViewById<TextView>(R.id.header_title)?.let {

            it.text =
                if (resTitleId > 0) rootView.context.getString(resTitleId)
                else item.name.capitalize()
        }
    }
}