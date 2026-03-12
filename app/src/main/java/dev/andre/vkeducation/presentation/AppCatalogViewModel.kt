package dev.andre.vkeducation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.andre.vkeducation.presentation.appdetails.App
import dev.andre.vkeducation.presentation.appdetails.Category
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppCatalogViewModel: ViewModel() {

    private val _state = MutableStateFlow(AppCatalogState())
    val state: StateFlow<AppCatalogState> = _state.asStateFlow()

    init {
        loadApps()
    }

    fun handleEvent(event: AppCatalogEvent) {
        when (event) {
            is AppCatalogEvent.OnAppClick -> {

            }
            is AppCatalogEvent.OnRefresh -> {
                loadApps()
            }
            is AppCatalogEvent.OnCategoryClick -> {

            }
        }
    }

    private fun loadApps() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            delay(1000)

            val apps = getMockApps()

            _state.update { it.copy(
                apps = apps,
                isLoading = false
            ) }
        }
    }

    private fun getMockApps(): List<App> = listOf(
        App(
            name = "Гильдия Героев: Экшен ММО РПГ",
            developer = "VK Play",
            category = Category.GAME,
            ageRating = 12,
            size = 223.7f,
            screenshotUrlList = listOf(
                "https://static.rustore.ru/imgproxy/-y8kd-4B6MQ-1OKbAbnoAIMZAzvoMMG9dSiHMpFaTBc/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/dfd33017-e90d-4990-aa8c-6f159d546788.jpg@webp"
            ),
            iconUrl = "https://static.rustore.ru/imgproxy/APsbtHxkVa4MZ0DXjnIkSwFQ_KVIcqHK9o3gHY6pvOQ/preset:web_app_icon_62/plain/https://static.rustore.ru/apk/393868735/content/ICON/3f605e3e-f5b3-434c-af4d-77bc5f38820e.png@webp",
            description = "Легендарный рейд героев в Фэнтези РПГ"
        ),
        App(
            name = "World of Tanks Blitz",
            developer = "Wargaming Group",
            category = Category.GAME,
            ageRating = 12,
            size = 350.5f,
            screenshotUrlList = emptyList(),
            iconUrl = "https://play-lh.googleusercontent.com/_RzS6ZQpOyhIZpy5wvhT6OQVg8mhGmLLdUAUFPY-BOjWc3ITv83Cv4Y4J5pAJDxM1A=w240-h480-rw",
            description = "Танковая ММО экшен-игра"
        ),
        App(
            name = "PUBG Mobile",
            developer = "Tencent Games",
            category = Category.GAME,
            ageRating = 16,
            size = 845.2f,
            screenshotUrlList = emptyList(),
            iconUrl = "https://play-lh.googleusercontent.com/lbqT2tBknvjZjtA7tSXk6wm6f6W9_Fco7PgtTZDOo2BTd6qE5xxNlqZxT6s7QdHLZg=w240-h480-rw",
            description = "Королевская битва"
        ),

        App(
            name = "VK",
            developer = "VK",
            category = Category.APP,
            ageRating = 12,
            size = 95.3f,
            screenshotUrlList = emptyList(),
            iconUrl = "https://play-lh.googleusercontent.com/OZ6L6hWYAD4UuW3XjiQc4BljH1OEcLyaKqGBaCmH8myiIMKJqad8n_4dsK8SDPc6bO8=w240-h480-rw",
            description = "Социальная сеть"
        ),
        App(
            name = "Telegram",
            developer = "Telegram FZ-LLC",
            category = Category.APP,
            ageRating = 12,
            size = 67.8f,
            screenshotUrlList = emptyList(),
            iconUrl = "https://play-lh.googleusercontent.com/ZU9cSsyNJ3e0jcyRTTD5dNGzVtU9zR8H_TuZPVp13NWEyE4mRzVvYaqhG54Zu9UmsA=w240-h480-rw",
            description = "Мессенджер"
        ),
        App(
            name = "Яндекс Карты",
            developer = "Yandex",
            category = Category.APP,
            ageRating = 6,
            size = 156.4f,
            screenshotUrlList = emptyList(),
            iconUrl = "https://play-lh.googleusercontent.com/s9iZGRYv8tz1wXWvO6zNxO23zU7oM-lccvU0iVHkW1tCr78dTksRRtCcZ8pUZb1UzA=w240-h480-rw",
            description = "Навигация и карты"
        ),
        App(
            name = "СберБанк Онлайн",
            developer = "Sberbank",
            category = Category.APP,
            ageRating = 6,
            size = 189.2f,
            screenshotUrlList = emptyList(),
            iconUrl = "https://play-lh.googleusercontent.com/6KjD7t2P9ZQr_2ZJ9Z5Q5R5X5Y5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5=w240-h480-rw",
            description = "Мобильный банк"
        )
    )
}


data class AppCatalogState(
    val apps: List<App> = emptyList(),
    val isLoading: Boolean = false,
)

sealed class AppCatalogEvent {
    data class OnAppClick(val app: App) : AppCatalogEvent()
    object OnRefresh : AppCatalogEvent()
    data class OnCategoryClick(val category: Category) : AppCatalogEvent()
}