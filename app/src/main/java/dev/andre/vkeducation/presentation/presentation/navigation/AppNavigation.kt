package dev.andre.vkeducation.presentation.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.andre.vkeducation.presentation.presentation.appcatalog.AppCatalogRoute
import dev.andre.vkeducation.presentation.presentation.appdetails.AppDetailsRoute

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Route.Catalog.route
    ) {
        composable(Route.Catalog.route) {
            AppCatalogRoute(
                onAppClick = { app ->
                    navController.navigate(
                        Route.Details.createRoute(Uri.encode(app.name))
                    )
                }
            )
        }
        
        composable(Route.Details.route) { backStackEntry ->
            val appName = Uri.decode(backStackEntry.arguments?.getString(Route.Details.ARG_APP_NAME) ?: "")
            AppDetailsRoute(
                appName = appId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
