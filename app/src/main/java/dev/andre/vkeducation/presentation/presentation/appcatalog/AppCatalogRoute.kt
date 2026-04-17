package dev.andre.vkeducation.presentation.presentation.appcatalog

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import dev.andre.vkeducation.presentation.domain.model.AppCatalog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCatalogRoute(
    onAppClick: (AppCatalog) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AppCatalogViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

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

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                viewModel.updateScrollIndex(index)
            }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.refreshOnResume()
        }
    }


    when(val currentState = state){
        is AppCatalogState.Content ->
            AppCatalogScreen(
                state = currentState,
                onAppClick = onAppClick,
                modifier = modifier,
                onRefresh = { viewModel.observeApps() },
                isRefreshing = isRefreshing,
                snackbarHostState = snackbarHostState,
                onIconClick = { viewModel.showHelloSnackbar() },
                scrollIndex = scrollIndex,
                listState = listState,
                onToggleWishList = { id ->
                    viewModel.toggleWishList(id)
                },
                onFilterCategory = { category ->
                    viewModel.filterByCategory(category)
                },
                onFilterWishList = { onlyWishList ->
                    viewModel.filterByWishList(onlyWishList)
                },
                onReset = { viewModel.resetFilter() }
            )
        AppCatalogState.Error ->
            ErrorContent()
        AppCatalogState.Loading ->
            LoadingContent()

        AppCatalogState.Offline ->
            OfflineContent()
    }
}