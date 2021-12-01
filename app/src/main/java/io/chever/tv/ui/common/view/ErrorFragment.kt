package io.chever.tv.ui.common.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.leanback.app.ErrorSupportFragment
import io.chever.tv.R
import io.chever.tv.common.extension.Extensions.isResourceExist
import io.chever.tv.ui.common.models.ErrorBuilder
import timber.log.Timber

/**
 * TODO: document class
 */
class ErrorFragment(
    private val builder: ErrorBuilder
) : ErrorSupportFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Don't work well
        //title = getString(R.string.app_name)

        // Setup message =>
        message = builder.message

        // Setup image drawable =>
        setupImageDrawable()

        // Setup button =>
        setupButton(view)
    }


    //#region Public methods

    fun show(): ErrorFragment {

        try {

            builder.fragmentManager.beginTransaction()
                .replace(builder.containerId, this)
                .commit()

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
        }

        return this
    }

    fun remove() {

        try {

            builder.fragmentManager.beginTransaction()
                .remove(this)
                .commit()

        } catch (ex: Exception) {

            Timber.e(ex, ex.message)
        }
    }

    //#endregion


    //#region Private methods

    private fun setupImageDrawable() {

        if (requireContext().isResourceExist(builder.imageResId)) {

            AppCompatResources.getDrawable(requireContext(), builder.imageResId)?.let {

                imageDrawable =
                    it.toBitmap(IMAGE_DRAWABLE_SIZE, IMAGE_DRAWABLE_SIZE).toDrawable(resources)
            }
        }
    }

    private fun setupButton(rootView: View) {

        rootView.findViewById<Button>(R.id.button)?.let {

            it.backgroundTintList = AppCompatResources.getColorStateList(
                requireContext(),
                R.color.secondaryLightColor
            )
        }

        buttonText = builder.textButton

        setButtonClickListener {
            builder.callbackButton?.invoke(requireContext())
        }
    }

    //#endregion


    companion object {

        const val IMAGE_DRAWABLE_SIZE = 72

        fun create(builder: ErrorBuilder): ErrorFragment {

            return ErrorFragment(builder)
        }
    }
}