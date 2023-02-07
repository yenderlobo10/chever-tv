package io.chever.apptv.ui.home.component

import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRowPresenter
import androidx.leanback.widget.PageRow
import androidx.lifecycle.lifecycleScope
import io.chever.apptv.ui.home.enums.HomePagesMenuItem
import io.chever.apptv.ui.home.presenter.HomeMenuItemPresenterSelector
import io.chever.apptv.ui.home.screen.HomeFragment
import kotlinx.coroutines.delay

class HomePagesMenu(
    private val homeFragment: HomeFragment
) {

    @Suppress("MemberVisibilityCanBePrivate")
    val adapter = ArrayObjectAdapter(ListRowPresenter())


    fun create() {

        initMenu()
        addMenuPresenter()
        initPagesMenuFragmentFactory()
    }

    private fun initPagesMenuFragmentFactory() {

        with(homeFragment) {

            adapter = this@HomePagesMenu.adapter
            mainFragmentRegistry?.registerFragment(
                PageRow::class.java,
                HomePagesMenuFragmentFactory()
            )
        }
    }

    private fun initMenu() {

        beforeInitMenu()

        HomePagesMenuItem.values().forEach { item ->

            val pageRow = PageRow(item.createHeaderItem())
            adapter.add(pageRow)
        }
    }

    private fun beforeInitMenu() {

        if (adapter.size() > 0) adapter.clear()

        homeFragment.lifecycleScope.launchWhenStarted {
            delay(300)
            homeFragment.startHeadersTransition(false)
        }
    }

    private fun addMenuPresenter() {

        homeFragment.setHeaderPresenterSelector(
            HomeMenuItemPresenterSelector()
        )
    }


    private fun HomePagesMenuItem.createHeaderItem() = HeaderItem(
        this.id,
        this.name
    )
}