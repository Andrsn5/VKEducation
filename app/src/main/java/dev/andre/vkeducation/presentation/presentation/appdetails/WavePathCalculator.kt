package dev.andre.vkeducation.presentation.presentation.appdetails

import android.graphics.PathMeasure as AndroidPathMeasure
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import kotlin.math.sin

class WavePathCalculator {

    data class WaveParams(
        val width: Float,
        val height: Float,
        val strokeWidth: Float,
        val cornerRadius: Float,
        val amplitude: Float = 8f,
        val frequency: Float = 1f,
        val phaseMultiplier: Float = 8f
    )

    fun createRoundedRectPath(params: WaveParams): Path {
        val inset = params.strokeWidth / 2f
        return Path().apply {
            addRoundRect(
                RoundRect(
                    left = inset,
                    top = inset,
                    right = params.width - inset,
                    bottom = params.height - inset,
                    cornerRadius = CornerRadius(params.cornerRadius, params.cornerRadius)
                )
            )
        }
    }

    fun calculateWavePath(
        basePath: Path,
        progress: Float,
        params: WaveParams
    ): Path {
        if (progress <= 0f) return Path()

        val androidPath = basePath.asAndroidPath()
        val measure = AndroidPathMeasure(androidPath, false)
        val length = measure.length

        val startOffset = length * 1.50f
        val step = 2f
        val wavePath = Path()

        val pos = FloatArray(2)
        val tan = FloatArray(2)
        var isFirst = true

        var distance = 0f
        val maxDistance = length * progress.coerceIn(0f, 1f)

        while (distance < maxDistance) {
            val d = (distance + startOffset) % length

            measure.getPosTan(d, pos, tan)

            val normalX = -tan[1]
            val normalY = tan[0]

            val t = distance / length
            val phase = progress * params.phaseMultiplier
            val waveOffset = sin(t * params.frequency + phase) * params.amplitude

            val x = pos[0] + normalX * waveOffset
            val y = pos[1] + normalY * waveOffset

            if (isFirst) {
                wavePath.moveTo(x, y)
                isFirst = false
            } else {
                wavePath.lineTo(x, y)
            }

            distance += step
        }

        return wavePath
    }
}
