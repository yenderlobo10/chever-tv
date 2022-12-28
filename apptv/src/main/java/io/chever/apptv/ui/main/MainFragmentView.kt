package io.chever.apptv.ui.main

import androidx.fragment.app.Fragment
import io.chever.apptv.ui.home.screen.HomeFragment
import io.chever.apptv.ui.launch.LaunchFragment

sealed class MainFragmentView(
    val fragment: Fragment,
    val tag: String
) {

    object Launch : MainFragmentView(
        fragment = LaunchFragment(),
        tag = LaunchFragment.TAG
    )

    object Home : MainFragmentView(
        fragment = HomeFragment(),
        tag = HomeFragment.TAG
    )
}
