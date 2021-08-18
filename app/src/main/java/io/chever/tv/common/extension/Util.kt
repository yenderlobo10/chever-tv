package io.chever.tv.common.extension

import android.content.Context
import io.chever.tv.R
import io.chever.tv.common.extension.Extensions.fromJson
import io.chever.tv.ui.common.models.RowHeaderItem
import java.util.*
import kotlin.random.Random

object Util {

    /**
     * Generate random GUID.
     *
     * Example: eee3effd-8cbd-423d-be44-9c86624468c8
     */
    fun genRandomGuid(): String = UUID.randomUUID().toString()

    /**
     * Generate random [Long] number id.
     */
    fun genRandomLongId(): Long = Random.nextLong(until = 999999999L)

    /**
     * Generate random [Int] number id.
     */
    fun genRandomIntId(): Int = Random.nextInt(until = 999999999)


    /**
     * TODO: document function
     */
    fun createMoviesCollectionsByRawJson(context: Context): Array<RowHeaderItem> {

        val rowType = arrayOf<RowHeaderItem>()::class.java

        return context.resources
            .openRawResource(R.raw.movies_browse_collections)
            .fromJson(rowType)!!
    }

    /**
     * TODO: document function
     */
    fun createUrlYouTubeVideo(key: String) = Constants.youtubeVideoBaseUrl.plus(key)
}