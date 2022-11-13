package io.chever.tv.ui.player.subtitles

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.leanback.preference.LeanbackPreferenceFragmentCompat
import androidx.lifecycle.lifecycleScope
import androidx.preference.Preference
import androidx.preference.PreferenceScreen
import io.chever.tv.R
import io.chever.tv.api.opensubtitles.domain.models.OSSubtitle
import io.chever.tv.api.opensubtitles.domain.models.OSSubtitlesResult
import io.chever.tv.common.extension.AppResult
import io.chever.tv.common.extension.DateTimeExtensions
import io.chever.tv.common.extension.DateTimeExtensions.toFormat
import io.chever.tv.common.extension.Extensions.showShortToast
import io.chever.tv.common.extension.FSymbols
import io.chever.tv.ui.common.models.PlayVideo
import kotlinx.coroutines.launch

class SubtitlesPreferenceScreen(
    private val playVideo: PlayVideo
) : LeanbackPreferenceFragmentCompat() {

    private lateinit var screen: PreferenceScreen
    private val keyLoadingPreference = "preference-loading"
    private val loadingPreference by lazy { createLoadingPreference() }
    private val viewModel by viewModels<SubtitlesViewModel>()

    override fun onCreatePreferences(
        savedInstanceState: Bundle?,
        rootKey: String?
    ) {

        screen = preferenceManager.createPreferenceScreen(requireContext()).apply {
            title = "Subtitulos"
            isPersistent = false
        }

        this.preferenceScreen = screen

        screen.addPreference(loadingPreference)
        findSubtitles()
    }

    private fun findSubtitles() {

        lifecycleScope.launchWhenStarted {
            viewModel.findSubtitles(playVideo).collect {

                when (it) {

                    is AppResult.Loading -> showLoadingPreference()

                    is AppResult.Success -> {
                        screen.removePreference(loadingPreference)
                        createSubtitlesListFromResult(it.data)
                    }

                    else -> {
                        requireContext().showShortToast(R.string.app_unknown_error_three)
                        requireActivity().finish()
                    }
                }
            }
        }
    }

    private fun createSubtitlesListFromResult(
        result: OSSubtitlesResult
    ) {

        if (result.data.isEmpty()) {
            screen.addPreference(createEmptyPreference())
            return
        }

        result.data.forEach { subtitle ->

            Preference(requireContext()).apply {

                val date =
                    subtitle.attributes.uploadDate?.toFormat(DateTimeExtensions.Pattern.DateTwo)
                val dateText = "${FSymbols.calendar} $date"

                val downloadCount = subtitle.attributes.downloadCount
                val downloadCountText = "${FSymbols.downArrow} $downloadCount"

                val langFlag = when (subtitle.attributes.language) {
                    "es" -> FSymbols.flagSpain
                    else -> FSymbols.flagEnglish
                }
                val releaseTitle = subtitle.attributes.release

                key = subtitle.id
                title = releaseTitle
                summary = "$langFlag    $dateText  $downloadCountText"

                onPreferenceClickListener = Preference.OnPreferenceClickListener {
                    whenSubtitlePreferenceClick(
                        subtitle = subtitle
                    )
                    true
                }

                screen.addPreference(this)
            }
        }
    }


    private fun whenSubtitlePreferenceClick(
        subtitle: OSSubtitle
    ) {

        lifecycleScope.launch {

            val file = subtitle.attributes.files.first()

            viewModel.downloadSubtitle(file).collect {

                when (it) {

                    is AppResult.Loading -> showLoadingPreference()

                    is AppResult.Success -> {
                        screen.removePreference(loadingPreference)

                        requireActivity().setResult(Activity.RESULT_OK, Intent().apply {
                            putExtra(PARAM_SUBTITLE_DOWNLOAD, it.data)
                        })
                        requireActivity().finish()
                    }

                    else -> {
                        screen.removePreference(loadingPreference)
                        requireContext().showShortToast(R.string.app_unknown_error_one)
                    }
                }
            }
        }
    }

    private fun createEmptyPreference(): Preference =
        Preference(requireContext()).apply {
            key = "preference-empty"
            title = "Subtitles not found"
            isEnabled = false
        }

    private fun showLoadingPreference() {
        if (isShowLoadingPreference().not())
            screen.addPreference(loadingPreference)
    }

    private fun isShowLoadingPreference(): Boolean =
        screen.findPreference<Preference?>(keyLoadingPreference) != null

    private fun createLoadingPreference(): Preference =
        Preference(requireContext()).apply {
            key = keyLoadingPreference
            layoutResource = R.layout.fragment_dialog_loader
            order = 0
        }

    companion object {

        const val PARAM_SUBTITLE_DOWNLOAD = "chever-download-subtitle"
    }
}