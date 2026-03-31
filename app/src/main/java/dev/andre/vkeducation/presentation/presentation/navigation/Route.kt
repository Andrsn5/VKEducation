package dev.andre.vkeducation.presentation.presentation.navigation

sealed interface Route {
    val route: String
     data object Catalog : Route {
         override val route: String = "catalog"
     }
     data object Details : Route {
         const val ARG_APP_ID = "appId"
         override val route: String = "details/{$ARG_APP_ID}"
         fun createRoute(appId: String): String = "details/${appId}"
     }

}