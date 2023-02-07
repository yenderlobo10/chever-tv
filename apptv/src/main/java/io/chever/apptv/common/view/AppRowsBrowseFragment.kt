package io.chever.apptv.common.view

import android.os.Bundle
import android.view.View
import androidx.leanback.app.BaseSupportFragment
import androidx.leanback.app.ProgressBarManager
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.ListRowPresenter
import io.chever.apptv.R
import io.chever.shared.extension.showShortToast
import io.chever.shared.observability.AppLogger

abstract class AppRowsBrowseFragment : RowsSupportFragment() {

    private lateinit var progressManager: ProgressBarManager
    protected val rowsCollectionsAdapter = ArrayObjectAdapter(ListRowPresenter())


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        whenViewCreated()
    }

    private fun whenViewCreated() {

        try {

            beforeSetupUI()

        } catch (ex: Exception) {

            AppLogger.error(ex)
            requireContext().showShortToast(R.string.app_unknown_error_one)
        }
    }

    open fun beforeSetupUI() {

        this.progressManager = (requireParentFragment() as BaseSupportFragment).progressBarManager
        this.adapter = this.rowsCollectionsAdapter

        setupUI()
    }

    protected fun showProgress() = progressManager.show()

    protected fun hideProgress() = progressManager.hide()

    abstract fun setupUI()
}