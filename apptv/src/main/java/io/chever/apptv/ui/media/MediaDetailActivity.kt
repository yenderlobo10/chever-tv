package io.chever.apptv.ui.media

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint
import io.chever.apptv.R
import io.chever.apptv.ui.media.enums.MediaFragmentView
import io.chever.domain.model.media.MediaItem
import io.chever.shared.extension.getSerializableExtra
import io.chever.shared.extension.showLongToast
import io.chever.shared.observability.AppLogger

@AndroidEntryPoint
class MediaDetailActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {

            setupUI()

        } catch (ex: Exception) {

            whenSetupUIError(ex)
        }
    }

    private fun setupUI() {

        val instanceParams = InstanceParams.createFromIntent(intent)
        val viewFragment = MediaFragmentView.fromMediaItem(instanceParams.mediaItem)

        supportFragmentManager.beginTransaction()
            .add(android.R.id.content, viewFragment.fragment, viewFragment.tag)
            .commit()
    }


    private fun whenSetupUIError(ex: Throwable) {

        AppLogger.error(ex)
        this.showLongToast(R.string.app_unknown_error_two)
        finish()
    }


    class InstanceParams(val mediaItem: MediaItem) {

        companion object {

            const val MEDIA_ITEM_NAME = "chever-media-item-name"

            fun createFromIntent(intent: Intent) = InstanceParams(
                mediaItem = intent.getSerializableExtra<MediaItem>(MEDIA_ITEM_NAME)
            )
        }

        fun launch(context: Context) {
            context.startActivity(this.createIntent(context))
        }

        fun createIntent(context: Context) =
            Intent(context, MediaDetailActivity::class.java).apply {
                putExtra(MEDIA_ITEM_NAME, mediaItem)
            }
    }
}