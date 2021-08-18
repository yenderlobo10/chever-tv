package io.chever.tv.ui.player

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

/**
 * TODO: document class
 */
class PlayerActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, VideoPlayerFragment())
                .commit()
        }
    }

    companion object {

        const val PARAM_VIDEO_ITEM = "chever-param-play-video"
    }
}