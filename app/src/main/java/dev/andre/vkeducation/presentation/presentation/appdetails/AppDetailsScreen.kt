package dev.andre.vkeducation.presentation.presentation.appdetails

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.andre.vkeducation.R
import dev.andre.vkeducation.presentation.domain.App
import dev.andre.vkeducation.presentation.domain.Category
import dev.andre.vkeducation.presentation.presentation.appcatalog.ErrorContent
import dev.andre.vkeducation.presentation.presentation.appcatalog.LoadingContent
import dev.andre.vkeducation.presentation.presentation.theme.VkEducationTheme



@Composable
fun AppDetailsScreen(
    appName: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    appDetails: App?
) {
    val context = LocalContext.current
    val underDevelopmentText = stringResource(R.string.under_developement)

    var descriptionCollapsed by remember { mutableStateOf(false) }

    if (appDetails == null) {
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
                app = appDetails,
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
                screenshotUrlList = appDetails.screenshotUrlList,
                contentPadding = PaddingValues(horizontal = 16.dp),
            )

            Spacer(Modifier.height(12.dp))

            AppDescription(
                description = appDetails.description,
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
                name = appDetails.developer,
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
            appName = "VK",
            modifier = Modifier.fillMaxSize(),
            onBackClick = {},
            appDetails = App(
                id = "1",
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