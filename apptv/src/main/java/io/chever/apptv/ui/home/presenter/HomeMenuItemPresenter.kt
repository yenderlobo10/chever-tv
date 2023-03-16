package io.chever.apptv.ui.home.presenter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.leanback.widget.PageRow
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.RowHeaderPresenter
import io.chever.apptv.R
import io.chever.apptv.databinding.HomePagesMenuItemBinding
import io.chever.apptv.ui.home.enums.HomePagesMenuItem

internal class HomeMenuItemPresenter(
    private val pagesMenuItem: HomePagesMenuItem
) : RowHeaderPresenter() {

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(
        parent: ViewGroup?
    ): Presenter.ViewHolder {

        val inflater = LayoutInflater.from(parent?.context)
        val itemView = inflater.inflate(R.layout.home_pages_menu_item, null)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(
        viewHolder: Presenter.ViewHolder?,
        item: Any?
    ) {

        viewHolder?.let {

            (item as PageRow).let {

                viewHolder.bindViewHolderItem()
            }
        }
    }

    private fun Presenter.ViewHolder.bindViewHolderItem() {

        val binding = HomePagesMenuItemBinding.bind(this.view)
        this.view.isFocusable = true

        binding.bindItemIcon()
        binding.bindItemTitle()
    }

    private fun HomePagesMenuItemBinding.bindItemIcon() {

        ContextCompat.getDrawable(
            this.root.context,
            pagesMenuItem.resIdIcon
        )?.let {

            this.headerIcon.setImageDrawable(it)
        }
    }

    private fun HomePagesMenuItemBinding.bindItemTitle() {

        this.headerTitle.text = this.root.context.getString(pagesMenuItem.resIdTitle)
    }

}