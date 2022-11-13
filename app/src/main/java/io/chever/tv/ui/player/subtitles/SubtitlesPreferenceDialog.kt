package io.chever.tv.ui.player.subtitles

import androidx.leanback.preference.LeanbackSettingsFragmentCompat
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen
import io.chever.tv.ui.common.models.PlayVideo

class SubtitlesPreferenceDialog(
    private val playVideo: PlayVideo
) : LeanbackSettingsFragmentCompat() {

    override fun onPreferenceStartInitialScreen() {
        startPreferenceFragment(
            SubtitlesPreferenceScreen(
                playVideo = playVideo
            )
        )
    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat?,
        pref: Preference?
    ): Boolean = false

    override fun onPreferenceStartScreen(
        caller: PreferenceFragmentCompat?,
        pref: PreferenceScreen?
    ): Boolean = false
}