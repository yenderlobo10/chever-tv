package io.chever.tv.ui.common.models

import android.content.Context
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import io.chever.tv.R

/**
 * TODO: document class
 */
data class ErrorBuilder(

    val fragmentManager: FragmentManager,

    var message: CharSequence,
    var textButton: String? = null,
    var imageResId: Int = 0,

    @IdRes
    val containerId: Int = R.id.scale_frame,

    var callbackButton: ((context: Context) -> Unit)? = null
)
