package io.chever.apptv.ui.media.enums

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.chever.apptv.R

enum class MediaDetailAction(
    val id: Long,
    @StringRes val resTitle: Int,
    @StringRes val resSubtitle: Int? = null,
    @DrawableRes val resIcon: Int? = null
) {

    Play(
        id = 1,
        resTitle = R.string.media_detail_action_play_label,
        resIcon = R.drawable.ic_play
    ),

    Trailer(
        id = 2,
        resTitle = R.string.media_detail_action_trailer_label
    ),

    MyList(
        id = 3,
        resTitle = R.string.media_detail_action_my_list_label,
        resIcon = R.drawable.ic_add
    );

    companion object {

        fun createFromId(
            id: Long
        ): MediaDetailAction? = when (id) {
            Play.id -> Play
            Trailer.id -> Trailer
            MyList.id -> MyList
            else -> null
        }
    }
}