package io.chever.tv.ui.common.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.FragmentActivity
import com.orhanobut.logger.Logger
import io.chever.tv.R

/**
 * TODO: document class
 */
class LoaderDialogFragment private constructor(
    private val parentActivity: FragmentActivity
) : AppCompatDialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, R.style.Theme_Chever_LoaderDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(
            R.layout.fragment_dialog_loader,
            container,
            false
        )
    }


    //#region Public methods

    fun show(): LoaderDialogFragment {

        try {

            show(parentActivity.supportFragmentManager, TAG)

        } catch (ex: Exception) {

            Logger.e(ex.message!!, ex)
        }

        return this
    }

    //#endregion


    companion object {

        const val TAG = "@LoaderDialogFragment"


        /**
         * Create a new instance of [LoaderDialogFragment].
         *
         * @param isCancelable Set "true" if modal is closable when back press.
         */
        fun create(
            activity: FragmentActivity,
            isCancelable: Boolean = false
        ): LoaderDialogFragment {

            return LoaderDialogFragment(activity).also {

                it.isCancelable = isCancelable
            }
        }
    }
}