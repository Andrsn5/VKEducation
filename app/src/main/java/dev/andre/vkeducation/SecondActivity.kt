package dev.andre.vkeducation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.andre.vkeducation.ui.theme.VKEducationTheme


class SecondActivity: ComponentActivity() {

    companion object{
        const val KEY = "key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VKEducationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    SecondScreen(
                        receivedText = intent.getStringExtra(KEY) ?: "",
                        onBackPressed = { finish() }
                    )
                }
            }
        }
    }
}

