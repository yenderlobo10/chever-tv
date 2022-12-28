package io.chever.apptv.ui.home.screen

import android.os.Bundle
import android.view.View
import androidx.leanback.app.BrowseSupportFragment
import io.chever.apptv.R
import io.chever.apptv.ui.home.component.HomePagesMenu
import io.chever.shared.extension.showLongToast
import io.chever.shared.observability.AppLogger

class HomeFragment : BrowseSupportFragment() {

    private val pagesMenu = HomePagesMenu(this)


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        whenViewCreated()
    }

    private fun whenViewCreated() {

        try {

            setupUI()

        } catch (ex: Exception) {

            AppLogger.error(ex)
            requireContext().showLongToast(R.string.app_unknown_error_one)
        }
    }

    private fun setupUI() {

        this.title = getString(R.string.app_name)
        pagesMenu.create()
    }


    companion object {

        const val TAG = "chever-home-fragment"
    }
}