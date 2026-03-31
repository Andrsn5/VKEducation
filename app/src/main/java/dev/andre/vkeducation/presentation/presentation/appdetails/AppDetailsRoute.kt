package dev.andre.vkeducation.presentation.presentation.appdetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.andre.vkeducation.presentation.presentation.appcatalog.ErrorContent
import dev.andre.vkeducation.presentation.presentation.appcatalog.LoadingContent

@Composable
fun AppDetailsRoute(
    appName: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: AppDetailsViewModel = viewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(appName) {
        viewModel.loadAppDetails(appName)
    }

    when(val currentState = state) {
        is AppDetailsState.Content ->
            AppDetailsScreen(
                appName = appName,
                appDetails = currentState.app,
                modifier = modifier,
                onBackClick = onBackClick,
            )
        is AppDetailsState.Error ->
            ErrorContent()
        is AppDetailsState.Loading ->
            LoadingContent()
    }
}