package com.example.giftpanelcompose

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController

fun NavController.navigate(route: AppRoute) = navigate(route.route)

sealed class AppRoute(val route: String, val args: List<NamedNavArgument> = emptyList()) {
    object MainScreen : AppRoute("mainscreen")

    object GiftPanel : AppRoute("giftpanel")
}
