package io.chever.tv.common.extension

import android.content.Context
import io.chever.tv.CheverApp
import io.chever.tv.R
import io.chever.tv.api.themoviedb.domain.enums.TMImageSize
import io.chever.tv.common.extension.Extensions.fromJson
import io.chever.tv.common.extension.Extensions.getPropertyFromFile
import io.chever.tv.common.models.RowHeaderItem
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
     * Create a complete url of TheMovieDB API Images by path.
     * TODO: fix static leak context
     *
     * @param urlPath TMDb url path.
     * @param size TMDb supported size of image.
     */
    fun createTMDbImageUrl(
        urlPath: String?,
        size: TMImageSize = TMImageSize.W500
    ): String {

        return CheverApp.context.getPropertyFromFile(
            Constants.appPropertiesFileName,
            "theMovieDBImagesUrl"
        )
            .plus(size.value)
            .plus(urlPath)
    }

    /**
     * TODO: document function
     */
    fun createMoviesCollectionsByRawJson(context: Context): Array<RowHeaderItem> {

        val rowType = arrayOf<RowHeaderItem>()::class.java

        return context.resources
            .openRawResource(R.raw.movies_browse_collections)
            .fromJson(rowType)!!
    }
}