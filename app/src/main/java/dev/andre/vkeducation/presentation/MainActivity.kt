package dev.andre.vkeducation.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import dev.andre.vkeducation.presentation.presentation.navigation.AppNavigation
import dev.andre.vkeducation.presentation.presentation.theme.VkEducationTheme
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VkEducationTheme {
                AppNavigation(
                    modifier = Modifier.Companion
                        .fillMaxSize()
                        .safeDrawingPadding()
                )
            }
        }
    }
}