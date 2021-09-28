package io.chever.tv.ui.torrent

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.leanback.app.GuidedStepSupportFragment

class TorrentSelectActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GuidedStepSupportFragment.addAsRoot(
            this,
            TorrentSelectFragment(),
            android.R.id.content
        )
    }

    companion object {

        const val PARAM_MOVIE_DETAIL = "chever-torrent-movie-detail"
        const val PARAM_TORRENT_LIST = "chever-torrent-list-result"
    }
}