package io.chever.tv.ui.player.common

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

class TestPreferenceActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, TestPreferenceDialogFragment())
            .commit()
    }
}