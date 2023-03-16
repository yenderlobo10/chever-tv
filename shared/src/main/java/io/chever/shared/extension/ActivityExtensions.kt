package io.chever.shared.extension

import android.content.Intent
import android.os.Build
import java.io.Serializable


@Suppress(
    "UNCHECKED_CAST",
    "DEPRECATION",
    "EXTENSION_SHADOWED_BY_MEMBER"
)
inline fun <reified T : Serializable> Intent.getSerializableExtra(
    name: String
): T = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ->
        this.getSerializableExtra(name, T::class.java) ?: throw IllegalArgumentException()
    else ->
        this.getSerializableExtra(name) as T? ?: throw IllegalArgumentException()
}