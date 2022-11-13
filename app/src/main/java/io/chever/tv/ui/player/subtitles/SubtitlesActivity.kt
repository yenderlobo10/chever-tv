package io.chever.tv.ui.player.subtitles

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import io.chever.tv.R
import io.chever.tv.common.extension.Extensions.showShortToast
import io.chever.tv.ui.common.models.PlayVideo

class SubtitlesActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {

            setupSubtitlesDialog()

        } catch (ex: Exception) {

            showShortToast(R.string.app_unknown_error_one)
            finish()
        }
    }

    private fun setupSubtitlesDialog() {

        val playVideo = intent.getSerializableExtra(PARAM_PLAY_VIDEO) as PlayVideo

        supportFragmentManager.beginTransaction()
            .replace(
                android.R.id.content,
                SubtitlesPreferenceDialog(playVideo)
            )
            .commit()
    }

    companion object {

        const val PARAM_PLAY_VIDEO = "chever-play-video"
    }
}