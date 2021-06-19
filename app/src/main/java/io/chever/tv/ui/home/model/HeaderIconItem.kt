package io.chever.tv.ui.home.model

import androidx.leanback.widget.HeaderItem
import com.orhanobut.logger.Logger
import com.squareup.moshi.Json
import io.chever.tv.R

data class HeaderIconItem(

    @Json(name = "id")
    val mId: Long,

    val title: String,
    val resIconId: String,

    ) : HeaderItem(mId, title) {

    /**
     * Header items ids.
     * Sames that {id} in [R.raw.header_items]
     *
     */
    enum class Id(val id: Long) {

        Movies(1),

        Shows(2),

        /// Add new items if needed.

        None(-1L);


        companion object {

            fun valueOf(id: Long): Id {

                return try {

                    values().find { x -> x.id == id } ?: None

                } catch (ex: Exception) {

                    Logger.e(ex.message!!, ex)
                    None
                }
            }
        }
    }
}