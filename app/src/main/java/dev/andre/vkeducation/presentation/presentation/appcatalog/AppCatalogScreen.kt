package dev.andre.vkeducation.presentation.presentation.appcatalog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.andre.vkeducation.presentation.domain.model.AppCatalog
import dev.andre.vkeducation.presentation.domain.model.Category
import dev.andre.vkeducation.presentation.presentation.theme.VkEducationTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCatalogScreen(
    state: AppCatalogState.Content,
    onAppClick: (AppCatalog) -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onIconClick: () -> Unit,
    scrollIndex: Int,
    listState: LazyListState,
    onToggleWishList: (String) -> Unit,
    onFilterCategory: (Category) -> Unit,
    onFilterWishList: (Boolean) -> Unit,
    onReset: () -> Unit
    ) {
    var showFilterMenu by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            AppCatalogTopBar(
                onIconClick = onIconClick,
                onShowMenuClick = { showFilterMenu = true }
            )
        }
    ) { paddingValues ->
        Column(modifier = modifier
            .padding(paddingValues)){
            AnimatedVisibility(visible = !state.isOnline) {
                OfflineBanner()
            }
            AppCatalogContent(
                state = state,
                onRefresh = { onRefresh() },
                isRefreshing = isRefreshing,
                onAppClick = onAppClick,
                modifier = modifier,
                scrollIndex = scrollIndex,
                listState = listState,
                onToggleWishList = onToggleWishList,
            )
        }

    }

    if (showFilterMenu){
        ModalBottomSheet(
            onDismissRequest = {showFilterMenu = false},
            sheetState = sheetState
        ) {
            FilterSheetContent(
                currentParams = state.filterParams,
                onFilterCategory = onFilterCategory,
                onFilterWishList = onFilterWishList,
                onReset = onReset
            )
        }
    }
}





@Preview(showBackground = true)
@Composable
private fun PreviewAppCatalogScreen() {
    VkEducationTheme {
        AppCatalogScreen(
            state = AppCatalogState.Content(
                appCatalog = listOf(
                    AppCatalog(
                        id = "1",
                        name = "Гильдия Героев: Экшен ММО РПГ",
                        category = Category.GAMES,
                        iconUrl = "https://static.rustore.ru/imgproxy/APsbtHxkVa4MZ0DXjnIkSwFQ_KVIcqHK9o3gHY6pvOQ/preset:web_app_icon_62/plain/https://static.rustore.ru/apk/393868735/content/ICON/3f605e3e-f5b3-434c-af4d-77bc5f38820e.png@webp",
                        description = "Легендарный рейд героев в Фэнтези РПГ"
                    ),
                    AppCatalog(
                        id = "2",
                        name = "World of Tanks Blitz",
                        category = Category.GAMES,
                        iconUrl = "https://play-lh.googleusercontent.com/_RzS6ZQpOyhIZpy5wvhT6OQVg8mhGmLLdUAUFPY-BOjWc3ITv83Cv4Y4J5pAJDxM1A=w240-h480-rw",
                        description = "Танковая ММО экшен-игра"
                    ),
                    AppCatalog(
                        id = "3",
                        name = "VK",
                        category = Category.GAMES,
                        iconUrl = "https://play-lh.googleusercontent.com/OZ6L6hWYAD4UuW3XjiQc4BljH1OEcLyaKqGBaCmH8myiIMKJqad8n_4dsK8SDPc6bO8=w240-h480-rw",
                        description = "Социальная сеть"
                    )
                ),
                isOnline = false
            ),
            onAppClick = {},
            onRefresh = {},
            modifier = Modifier.fillMaxSize(),
            snackbarHostState = SnackbarHostState(),
            onIconClick = {},
            scrollIndex = 0,
            listState = rememberLazyListState(),
            onToggleWishList = {},
            isRefreshing = false,
            onFilterCategory = {},
            onFilterWishList = {},
            onReset = {}
        )
    }
}