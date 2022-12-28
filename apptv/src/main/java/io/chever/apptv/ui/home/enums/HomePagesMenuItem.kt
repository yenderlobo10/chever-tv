package io.chever.apptv.ui.home.enums

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.chever.apptv.R

enum class HomePagesMenuItem(
    val id: Long,
    @StringRes val resIdTitle: Int,
    @DrawableRes val resIdIcon: Int
) {

    Home(
        id = 1,
        resIdTitle = R.string.menu_item_home_title,
        resIdIcon = R.drawable.ic_house
    ),

    Movies(
        id = 2,
        resIdTitle = R.string.menu_item_movies_title,
        resIdIcon = R.drawable.ic_films
    )
}