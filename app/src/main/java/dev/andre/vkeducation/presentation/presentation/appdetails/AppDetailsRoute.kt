package dev.andre.vkeducation.presentation.presentation.appdetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.andre.vkeducation.presentation.presentation.appcatalog.ErrorContent
import dev.andre.vkeducation.presentation.presentation.appcatalog.LoadingContent
import dev.andre.vkeducation.presentation.presentation.appcatalog.OfflineContent

@Composable
fun AppDetailsRoute(
    appId: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: AppDetailsViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(appId) {
        viewModel.observeAppDetails(appId)
    }

    when(val currentState = state) {
        is AppDetailsState.Content ->
            AppDetailsScreen(
                appName = appId,
                state = currentState,
                modifier = modifier,
                onBackClick = onBackClick,
                onClickWishList = { viewModel.toggleWishList(appId) },
                onDownload = { viewModel.download(appId) },
                onDelete = { viewModel.delete(appId) },
            )
        is AppDetailsState.Error ->
            ErrorContent()
        is AppDetailsState.Loading ->
            LoadingContent()

        AppDetailsState.Offline ->
            OfflineContent()
    }
}