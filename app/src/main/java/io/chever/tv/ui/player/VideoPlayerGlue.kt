package io.chever.tv.ui.player

import android.content.Context
import androidx.leanback.media.PlaybackTransportControlGlue
import androidx.leanback.media.PlayerAdapter
import androidx.leanback.widget.Action
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.PlaybackControlsRow

class VideoPlayerGlue<T : PlayerAdapter>(
    context: Context,
    adapter: T,
) : PlaybackTransportControlGlue<T>(context, adapter) {

    private var onActionSubtitlesClicked: ((action: Action) -> Unit)? = null

    private val subtitles: PlaybackControlsRow.ClosedCaptioningAction =
        PlaybackControlsRow.ClosedCaptioningAction(context)

    override fun onCreateSecondaryActions(secondaryActionsAdapter: ArrayObjectAdapter?) {

        secondaryActionsAdapter?.add(subtitles)
    }

    override fun onActionClicked(action: Action?) {
        super.onActionClicked(action)

        action?.let {
            if (it.id == subtitles.id)
                onActionSubtitlesClicked?.invoke(it)
        }
    }

    fun addOnActionSubtitlesClicked(
        callback: (action: Action) -> Unit
    ) {
        this.onActionSubtitlesClicked = callback
    }
}