package io.chever.apptv.ui.home.component

import androidx.fragment.app.Fragment
import androidx.leanback.app.BrowseSupportFragment.FragmentFactory
import androidx.leanback.widget.Row
import io.chever.apptv.ui.home.enums.HomePagesMenuItem
import io.chever.apptv.ui.home.screen.HomeBrowseFragment
import io.chever.apptv.ui.movies.screen.MoviesBrowseFragment

class HomePagesMenuFragmentFactory : FragmentFactory<Fragment>() {

    override fun createFragment(row: Any?): Fragment {

        val rowItem = row as Row
        val pageMenuItem = HomePagesMenuItem.values().find { x ->
            x.id == rowItem.id
        }

        return when (pageMenuItem) {

            HomePagesMenuItem.Home -> HomeBrowseFragment()
            HomePagesMenuItem.Movies -> MoviesBrowseFragment()
            else -> Fragment()
        }
    }

}