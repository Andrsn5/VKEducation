package dev.andre.vkeducation.presentation.presentation.appcatalog

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.andre.vkeducation.presentation.domain.model.AppCatalog
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCatalogRoute(
    onAppClick: (AppCatalog) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AppCatalogViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val scrollIndex by viewModel.scrollIndex.collectAsStateWithLifecycle()

    val listState = rememberLazyListState()

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when(event){
                is AppCatalogEvent.ShowSnackbar ->
                    snackbarHostState.showSnackbar(
                        message = context.getString(event.textResId)
                    )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.updateScrollIndex(listState.firstVisibleItemIndex)
        Timber.d("Saving scroll index = ${listState.firstVisibleItemIndex}")
    }

    when(val currentState = state){
        is AppCatalogState.Content ->
            AppCatalogScreen(
                state = currentState,
                onAppClick = onAppClick,
                modifier = modifier,
                onRefresh = { viewModel.loadApps() },
                snackbarHostState = snackbarHostState,
                onIconClick = { viewModel.showHelloSnackbar() },
                scrollIndex = scrollIndex,
                listState = listState
            )
        AppCatalogState.Error ->
            ErrorContent()
        AppCatalogState.Loading ->
            LoadingContent()
    }
}