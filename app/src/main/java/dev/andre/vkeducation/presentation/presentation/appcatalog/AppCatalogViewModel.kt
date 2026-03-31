package dev.andre.vkeducation.presentation.presentation.appcatalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.andre.vkeducation.R
import dev.andre.vkeducation.presentation.presentation.appdetails.Category
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppCatalogViewModel: ViewModel() {

    private val _state: MutableStateFlow<AppCatalogState> = MutableStateFlow(AppCatalogState.Loading)
    val state: StateFlow<AppCatalogState> = _state.asStateFlow()

    private val _events = Channel<AppCatalogEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun showHelloSnackbar() {
        viewModelScope.launch {
            _events.send(AppCatalogEvent.ShowSnackbar(R.string.hello))
        }
    }

    init {
        loadApps()
    }


     fun loadApps() {
        viewModelScope.launch {
            _state.update {  AppCatalogState.Loading }
            runCatching {
                delay(2000)

                val apps = getCatalogApps()

                _state.update {
                    AppCatalogState.Content(appCatalog = apps)
                }
            }.onFailure { e ->
                if (e is CancellationException) throw e
                _state.update { AppCatalogState.Error }
            }
        }
    }

    private fun getCatalogApps() = listOf(
        AppCatalog(
            name = "СберБанк Онлайн — с Салютом",
            category = Category.APP,
            iconUrl = "https://static.rustore.ru/imgproxy/lQKIdJKRbtJBX0dxbZueqU-a5TEP_-_yKjFjWljOsaE/preset:web_app_icon_62/plain/https://static.rustore.ru/apk/462271/content/ICON/f1b3c68a-b734-48ce-b62f-490208d3fa0e.png@webp",
            description = "Больше чем банк"
        ),

        AppCatalog(
            name = "Яндекс.Браузер — с Алисой",
            category = Category.APP,
            iconUrl = "https://static.rustore.ru/imgproxy/U4YtvpK16WhmZanjFxLbq1NabvyjyZJF8iAIFS8pDtw/preset:web_app_icon_160/plain/https://static.rustore.ru/apk/313257919/content/ICON/843c5040-0e09-41bb-958c-b7bacc912c2b.png@webp",
            description = "Быстрый и безопасный браузер"
        ),

        AppCatalog(
            name = "Почта Mail.ru",
            category = Category.APP,
            iconUrl = "https://static.rustore.ru/imgproxy/2wnsbc-wCmdbFYEdpH8uL3Jl4db6i7HE9Vj5079oh6Q/preset:web_app_icon_160/plain/https://static.rustore.ru/2026/3/11/7c/apk/332223/content/ICON/2ea61211-2ee2-469b-a08e-acc8a9f3b4c6.png@webp",
            description = "Почтовый клиент для любых ящиков"
        ),

        AppCatalog(
            name = "Яндекс Навигатор",
            category = Category.APP,
            iconUrl = "https://static.rustore.ru/imgproxy/FvKuW-aUKk34jUz1ZEPXebdfDR0ikU93-JJYC5_Oh4Y/preset:web_app_icon_160/plain/https://static.rustore.ru/apk/595135/content/ICON/32cb5e63-9c59-4280-9a6a-c808113be88f.png@webp",
            description = "Парковки и заправки — по пути"
        ),

        AppCatalog(
            name = "Мой МТС",
            category = Category.APP,
            iconUrl = "https://static.rustore.ru/imgproxy/uAJeOzFbun_tDiquzvvs_kieJ8ihjODiwCb7LGISdos/preset:web_app_icon_160/plain/https://static.rustore.ru/apk/336831/content/ICON/ea6c9e63-bd7f-486f-ac3f-3e9069ecf018.png@webp",
            description = "Центр экосистемы МТС"
        ),

        AppCatalog(
            name = "Яндекс — с Алисой",
            category = Category.APP,
            iconUrl = "https://static.rustore.ru/imgproxy/bZNt9jiZUOVXXOG0JdJQleTYIB2cFeE3MaWk7o897jE/preset:web_app_icon_160/plain/https://static.rustore.ru/2025/10/25/1e/apk/579007/content/ICON/939321c0-03f7-484d-9043-c0fb12736ef1.png@webp",
            description = "Поиск всегда под рукой"
        ),
        AppCatalog(
            name = "Гильдия Героев: Экшен ММО РПГ",
            category = Category.GAME,
            iconUrl = "https://static.rustore.ru/imgproxy/APsbtHxkVa4MZ0DXjnIkSwFQ_KVIcqHK9o3gHY6pvOQ/preset:web_app_icon_62/plain/https://static.rustore.ru/apk/393868735/content/ICON/3f605e3e-f5b3-434c-af4d-77bc5f38820e.png@webp",
            description = "Легендарный рейд героев в Фэнтези РПГ"
        ),
        AppCatalog(
            name = "World of Tanks Blitz",
            category = Category.GAME,
            iconUrl = "https://static.rustore.ru/imgproxy/3lFd0OGjC-yV-bX8ysi_vf8za6m_8bYcuwEZ4_PuaU4/preset:web_app_icon_62/plain/https://static.rustore.ru/2026/1/19/19/apk/2063496338/content/ICON/d0b231ac-760b-488d-b311-7376315b0c49.png@webp",
            description = "Танковая ММО экшен-игра"
        ),
        AppCatalog(
            name = "VK",
            category = Category.APP,
            iconUrl = "https://static.rustore.ru/imgproxy/PTo8g-Giv9VHYo7_Rwxw_1wC07KtDM7eSJgAfMlv53s/preset:web_app_icon_160/plain/https://static.rustore.ru/3f3d7180-6eb9-45ad-8706-f467c6dcf82a@webp",
            description = "Социальная сеть"
        )
    )
}