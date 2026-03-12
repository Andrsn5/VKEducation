package dev.andre.vkeducation.presentation


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.andre.vkeducation.R
import dev.andre.vkeducation.presentation.appdetails.App
import dev.andre.vkeducation.presentation.appdetails.Category
import dev.andre.vkeducation.presentation.theme.VkEducationTheme


@Composable
fun AppCatalogRoute(
    onAppClick: (App) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AppCatalogViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    VkEducationTheme {
        AppCatalogScreen(
            state = state,
            onEvent = viewModel::handleEvent,
            onAppClick = onAppClick,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCatalogScreen(
    state: AppCatalogState,
    onEvent: (AppCatalogEvent) -> Unit,
    onAppClick: (App) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pullToRefreshState = rememberPullToRefreshState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "menu"
                        )
                        Text(
                            text = stringResource(R.string.app_catalog_title),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.secondary
                ),
                actions = {
                    IconButton(onClick = { onEvent(AppCatalogEvent.OnRefresh) }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = stringResource(R.string.app_catalog_buttom_decription),
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = state.isLoading,
            onRefresh = { onEvent(AppCatalogEvent.OnRefresh) },
            state = pullToRefreshState,
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.primary)
            ,

        ) {
            if (state.isLoading && state.apps.isEmpty()) {
                // Показываем индикатор только при первой загрузке
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.apps.isEmpty()) {
                // Показываем пустое состояние
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.app_catalog_no_found_app),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.secondary)
                    ) {
                    items(state.apps) { app ->
                        AppListItem(
                            app = app,
                            onClick = { onAppClick(app) }
                        )
                    }
                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
private fun PreviewAppCatalogScreen() {
    VkEducationTheme {
        AppCatalogScreen(
            state = AppCatalogState(
                apps = listOf(
                    App(
                        name = "Гильдия Героев: Экшен ММО РПГ",
                        developer = "VK Play",
                        category = Category.GAME,
                        ageRating = 12,
                        size = 223.7f,
                        screenshotUrlList = emptyList(),
                        iconUrl = "https://static.rustore.ru/imgproxy/APsbtHxkVa4MZ0DXjnIkSwFQ_KVIcqHK9o3gHY6pvOQ/preset:web_app_icon_62/plain/https://static.rustore.ru/apk/393868735/content/ICON/3f605e3e-f5b3-434c-af4d-77bc5f38820e.png@webp",
                        description = "Легендарный рейд героев в Фэнтези РПГ"
                    ),
                    App(
                        name = "World of Tanks Blitz",
                        developer = "Wargaming Group",
                        category = Category.GAME,
                        ageRating = 12,
                        size = 350.5f,
                        screenshotUrlList = emptyList(),
                        iconUrl = "https://play-lh.googleusercontent.com/_RzS6ZQpOyhIZpy5wvhT6OQVg8mhGmLLdUAUFPY-BOjWc3ITv83Cv4Y4J5pAJDxM1A=w240-h480-rw",
                        description = "Танковая ММО экшен-игра"
                    ),
                    App(
                        name = "VK",
                        developer = "VK",
                        category = Category.APP,
                        ageRating = 12,
                        size = 95.3f,
                        screenshotUrlList = emptyList(),
                        iconUrl = "https://play-lh.googleusercontent.com/OZ6L6hWYAD4UuW3XjiQc4BljH1OEcLyaKqGBaCmH8myiIMKJqad8n_4dsK8SDPc6bO8=w240-h480-rw",
                        description = "Социальная сеть"
                    )
                ),
                isLoading = false
            ),
            onEvent = {},
            onAppClick = {}
        )
    }
}

