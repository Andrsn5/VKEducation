package dev.andre.vkeducation.presentation.presentation.appdetails

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.andre.vkeducation.R
import dev.andre.vkeducation.presentation.AppRepository
import dev.andre.vkeducation.presentation.presentation.theme.VkEducationTheme

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


@Composable
fun AppDetailsScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    appDetails: App?
) {
    val context = LocalContext.current
    val underDevelopmentText = stringResource(R.string.under_developement)

    var descriptionCollapsed by remember { mutableStateOf(false) }

    if (app == null) {
        AppDetailsNotFoundScreen(
            appName = appName, modifier = modifier, onBackClick = onBackClick
        )
    } else {
        Column(modifier) {
            Toolbar(
                onBackClick = {
                    onBackClick()
                    Toast.makeText(context, underDevelopmentText, Toast.LENGTH_SHORT).show()
                },
                onShareClick = {
                    Toast.makeText(context, underDevelopmentText, Toast.LENGTH_SHORT).show()
                },
            )

            Spacer(Modifier.height(8.dp))

            AppDetailsHeader(
                app = app,
                modifier = Modifier.padding(horizontal = 16.dp),
            )

            Spacer(Modifier.height(16.dp))
            InstallButton(
                onClick = {
                    Toast.makeText(context, underDevelopmentText, Toast.LENGTH_SHORT).show()
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(12.dp))

            ScreenshotsList(
                screenshotUrlList = app.screenshotUrlList,
                contentPadding = PaddingValues(horizontal = 16.dp),
            )

            Spacer(Modifier.height(12.dp))

            AppDescription(
                description = app.description,
                collapsed = descriptionCollapsed,
                onReadMoreClick = {
                    descriptionCollapsed = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )

            Spacer(Modifier.height(12.dp))

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.outlineVariant,
            )

            Spacer(Modifier.height(12.dp))

            Developer(
                name = app.developer,
                onClick = {
                    Toast.makeText(context, underDevelopmentText, Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
            )
        }
    }
}



@Preview
@Composable
private fun Preview() {
    VkEducationTheme {
        AppDetailsScreen(
            modifier = Modifier.fillMaxSize(),
            onBackClick = {},
            appDetails = App(
                name = "VK",
                developer = "VK",
                category = Category.APP,
                ageRating = 12,
                size = 95.3f,
                screenshotUrlList = emptyList(),
                iconUrl = "",
                description = "Социальная сеть"
            )
        )
    }
}