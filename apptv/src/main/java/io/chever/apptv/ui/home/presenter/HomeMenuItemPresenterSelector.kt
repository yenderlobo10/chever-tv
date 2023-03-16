package io.chever.apptv.ui.home.presenter

import androidx.leanback.widget.PageRow
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.PresenterSelector
import io.chever.apptv.ui.home.enums.HomePagesMenuItem

internal class HomeMenuItemPresenterSelector : PresenterSelector() {

    override fun getPresenter(item: Any?): Presenter {

        (item as PageRow).let {

            HomePagesMenuItem.values().find { x ->
                x.id == it.headerItem.id
            }?.let {

                return HomeMenuItemPresenter(
                    pagesMenuItem = it
                )
            }
        }

        return HomeMenuItemPresenter(
            pagesMenuItem = HomePagesMenuItem.Home
        )
    }
}