package dev.andre.vkeducation.presentation.presentation.appdetails

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun AppIconWithDownloadWave(
    iconUrl: String,
    downloadState: DownloadStatus,
    iconSize: Dp = 128.dp,
    modifier: Modifier = Modifier
) {
    val isActive = downloadState is DownloadStatus.Downloading

    val rawProgress: Float = when (downloadState) {
        is DownloadStatus.Downloading -> {
            val p = downloadState.progress
            when (p) {
                is Long -> p.toFloat() / 100f
                is Int -> p.toFloat() / 100f
                is Float -> p
                is Number -> p.toFloat() / 100f
                else -> 0f
            }.coerceIn(0f, 1f)
        }
        else -> 0f
    }

    val progress by animateFloatAsState(
        targetValue = rawProgress,
        animationSpec = tween(120),
        label = "progress"
    )

    val primaryColor = MaterialTheme.colorScheme.primary

    val calculator = remember { WavePathCalculator() }

    Box(
        modifier = modifier.size(iconSize),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = iconUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(iconSize)
                .clip(RoundedCornerShape(16.dp))
        )

        if (isActive) {
            Canvas(modifier = Modifier.size(iconSize)) {
                val params = WavePathCalculator.WaveParams(
                    width = size.width,
                    height = size.height,
                    strokeWidth = 6.dp.toPx(),
                    cornerRadius = 16.dp.toPx()
                )

                val basePath = calculator.createRoundedRectPath(params)
                val wavePath = calculator.calculateWavePath(basePath, progress, params)

                drawPath(
                    path = wavePath,
                    color = primaryColor,
                    style = Stroke(
                        width = params.strokeWidth,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }
        }
    }
}