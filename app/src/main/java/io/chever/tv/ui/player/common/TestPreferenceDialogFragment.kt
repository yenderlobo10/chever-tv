package io.chever.tv.ui.player.common

import android.content.Context
import android.os.Bundle
import androidx.leanback.preference.*
import androidx.preference.*
import io.chever.tv.common.extension.Extensions.showShortToast
import io.chever.tv.ui.common.view.LoaderDialogFragment

class TestPreferenceDialogFragment : LeanbackSettingsFragmentCompat() {

    override fun onPreferenceStartInitialScreen() {
        startPreferenceFragment(createPreferenceFragment())
    }

    override fun onPreferenceStartScreen(
        caller: PreferenceFragmentCompat?,
        pref: PreferenceScreen?
    ): Boolean {
        return false
    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat?,
        pref: Preference?
    ): Boolean {
        return false
    }


    private fun createPreferenceFragment() = AppPreferenceFragment.create(requireContext())


    class AppPreferenceFragment : LeanbackPreferenceFragmentCompat() {

        lateinit var appContext: Context


        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

            val screen = preferenceManager.createPreferenceScreen(appContext)

            screen.title = "Test Preference"
            screen.isPersistent = false
            preferenceScreen = screen

            screen.addPreference(Preference(appContext).apply {
                isPersistent = false
                title = "Button item"
                //summary = "item button summary"
                key = "button-key"
                onPreferenceClickListener = Preference.OnPreferenceClickListener { preference ->

                    LoaderDialogFragment.create(requireActivity(), isCancelable = true).show()

                    appContext.showShortToast(":: CLICK PREFERENCE ::")
                    true
                }
            })

            // TODO: only test
            for (i in 1..3) {


//                screen.addPreference(
//                    preferenceManager.createPreferenceScreen(appContext).apply {
//                        title = "Screen $i"
//                        isPersistent = false
//                        //isSelectable = true
//                        //isEnabled = true
//                    }
//                )

                val prefs = ListPreference(appContext)
                val multiPrefs = MultiSelectListPreference(appContext)

                prefs.apply {

                    //isSelectable = true
                    isPersistent = false
                    title = "Category $i"
                    dialogTitle = "Category $i"
                    //summary = "summary category $i"
                    key = "key-$i"

                    entries = listOf("Entry 1.$i", "Entry 2.$i", "Entry 3.$i").toTypedArray()
                    entryValues = listOf("value-1", "value-2", "value-3").toTypedArray()
                    value = "value-1"
                }

                screen.addPreference(prefs)
            }
        }

        companion object {

            fun create(context: Context) = AppPreferenceFragment().also {
                it.appContext = context
            }
        }
    }
}