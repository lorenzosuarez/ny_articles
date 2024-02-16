package com.challenge.presentation.navigation

import androidx.annotation.StringRes
import com.challenge.presentation.R

const val ART_ID = "ART_ID"

sealed class Screen(
    val route: String,
    @StringRes val title: Int,
    val showTopFilters: Boolean = true,
    val showBackButton: Boolean = false,
) {
    data object Home : Screen(
        title = R.string.home_title,
        route = "home",
    )

    data object Detail : Screen(
        title = R.string.detail_title,
        route = "detail/{$ART_ID}",
        showTopFilters = false,
        showBackButton = true,
    ) {
        fun createRoute(itemId: String) = "detail/$itemId"
    }

    companion object {
        val allScreens: List<Screen> by lazy {
            listOf(
                Home,
                Detail,
            )
        }
    }
}
