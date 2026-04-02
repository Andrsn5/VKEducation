package dev.andre.vkeducation.presentation.presentation.appcatalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.andre.vkeducation.presentation.domain.model.AppCatalog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCatalogContent(
    state: AppCatalogState.Content,
    onRefresh: () -> Unit,
    onAppClick: (AppCatalog) -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues
) {
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = false,
        onRefresh = onRefresh,
        state = pullToRefreshState,
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        if (state.appCatalog.isEmpty()) {
            EmptyContent()
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(MaterialTheme.colorScheme.secondary),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(
                    items = state.appCatalog,
                    key = { it.name }
                ) { app ->
                    AppListItem(
                        app = app,
                        onClick = { onAppClick(app) }
                    )
                }
            }
        }
    }
}