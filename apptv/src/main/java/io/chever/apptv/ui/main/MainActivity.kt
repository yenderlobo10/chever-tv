package io.chever.apptv.ui.main

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with((MainFragmentView.Launch)) {
            supportFragmentManager.beginTransaction()
                .add(android.R.id.content, this.fragment, this.tag)
                .commit()
        }
    }
}