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
import dev.andre.vkeducation.presentation.AppRepository
import dev.andre.vkeducation.presentation.presentation.theme.VkEducationTheme

@Composable
fun AppDetailsScreen(
    appName: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val app = remember(appName) {
        AppRepository.getAppByName(appName)
    }

    val context = LocalContext.current
    val underDevelopmentText = stringResource(R.string.under_developement)

    var descriptionCollapsed by remember { mutableStateOf(false) }

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
        app?.let {
            AppDetailsHeader(
                app = it,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }
        Spacer(Modifier.height(16.dp))
        InstallButton(
            onClick = {
                Toast.makeText(context, underDevelopmentText, Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(12.dp))
        app?.let {
            ScreenshotsList(
                screenshotUrlList = it.screenshotUrlList,
                contentPadding = PaddingValues(horizontal = 16.dp),
            )
        }
        Spacer(Modifier.height(12.dp))
        app?.let {
            AppDescription(
                description = it.description,
                collapsed = descriptionCollapsed,
                onReadMoreClick = {
                    descriptionCollapsed = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )
        }
        Spacer(Modifier.height(12.dp))
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.outlineVariant,
        )
        Spacer(Modifier.height(12.dp))
        app?.let {
            Developer(
                name = it.developer,
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
            appName = "Гильдия Героев: Экшен ММО РПГ",
            modifier = Modifier.fillMaxSize(),
            onBackClick = {},
        )
    }
}