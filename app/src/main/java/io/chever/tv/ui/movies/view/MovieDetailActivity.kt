package io.chever.tv.ui.movies.view

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import io.chever.tv.R

class MovieDetailActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
    }


    companion object {

        const val PARAM_ITEM_MOVIE = "chever-tv-item-movie"
        const val TRANSITION_SHARED_ELEMENT = "iv-poster-detail"
    }
}