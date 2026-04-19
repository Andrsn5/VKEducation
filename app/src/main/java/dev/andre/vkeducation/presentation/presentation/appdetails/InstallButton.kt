package dev.andre.vkeducation.presentation.presentation.appdetails

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.andre.vkeducation.R
import dev.andre.vkeducation.presentation.presentation.theme.VkEducationTheme

@Composable
fun InstallButton(
    onClick: () -> Unit,
    downloadState: DownloadStatus,

    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(vertical = 12.dp),
        modifier = modifier,
    ) {
        Text(text = mapText(downloadState))
    }
}

@ReadOnlyComposable
@Composable
private fun mapText(status: DownloadStatus): String =
    when (status) {
        is DownloadStatus.Prepare -> stringResource(R.string.install)
        is DownloadStatus.Started -> stringResource(R.string.begin)
        is DownloadStatus.Downloading -> stringResource(R.string.downloading, status.progress)
        is DownloadStatus.Installed -> stringResource(R.string.ready)
        is DownloadStatus.Error -> stringResource(R.string.error)
    }

@Preview
@Composable
private fun Preview() {
    VkEducationTheme {
        InstallButton(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            downloadState = DownloadStatus.Prepare
        )
    }
}