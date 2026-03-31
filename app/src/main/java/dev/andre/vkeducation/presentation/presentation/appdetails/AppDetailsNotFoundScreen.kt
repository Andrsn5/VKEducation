package dev.andre.vkeducation.presentation.presentation.appdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.andre.vkeducation.R

@Composable
fun AppDetailsNotFoundScreen(
    appName: String, modifier: Modifier = Modifier, onBackClick: () -> Unit
) {
    Column(modifier.fillMaxSize()) {
        Toolbar(
            onBackClick = onBackClick, onShareClick = onBackClick
        )

        Box(
            modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(R.string.app_not_found, appName))
        }
    }
}
