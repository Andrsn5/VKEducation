package dev.andre.vkeducation.presentation.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.andre.vkeducation.presentation.presentation.appcatalog.AppCatalogRoute
import dev.andre.vkeducation.presentation.presentation.appdetails.AppDetailsScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "catalog"
    ) {
        composable("catalog") {
            AppCatalogRoute(
                onAppClick = { app ->
                    val encodedName = Uri.encode(app.name)
                    navController.navigate("details/$encodedName")
                }
            )
        }
        
        composable("details/{appName}") { backStackEntry ->
            val appName = Uri.decode(backStackEntry.arguments?.getString("appName") ?: "")
            AppDetailsScreen(
                appName = appName,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
