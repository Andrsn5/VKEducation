## Стек

**Язык:** Kotlin 2.2.0  
**Архитектура:** Clean Architecture (Presentation → Domain → Data)

**Слой | Технологии**
---|---
**UI** | Jetpack Compose, Material 3, Navigation Component, Coil
**State** | ViewModel, Coroutines, Flow, StateFlow, Channel
**DI** | Hilt/Dagger
**Network** | Retrofit, OkHttp, Kotlinx Serialization
**Local DB** | Room
**Background** | WorkManager, HiltWorkerFactory
**Build** | Gradle KTS, KSP, ProGuard
**Logging** | Timber

## Ключевые решения

- **Offline-first** — Room кэширует данные, приложение работает без сети (NetworkMonitor Flow отслеживает состояние подключения в реальном времени)
- **WishList** — Room сохраняет лайкнутые данные, приложение не удаляет их без вашего разрешения
- **DownloadsList** — Room сохраняет список загруженных приложений с возможностью управления
- **Reactive UI** — Room Flow автоматически обновляет экран при изменении БД
- **Network Monitoring** — Реактивное отслеживание интернет-соединения через Flow с callbackFlow
- **Filtering System** — Система фильтрации по категориям, избранным и загруженным приложениям
- **State Preservation** — SavedStateHandle сохраняет позицию скролла при пересоздании экрана
- **Background Downloads** — WorkManager с интеграцией Hilt для фоновых задач загрузки
- **Notification Channel** — Система уведомлений для отслеживания загрузок
- **Debug/Release** — в debug-сборке работает с суффиксом .debug и флагом TESTING
- **Error Handling** — Channel для UI-событий (Snackbar) и корректная обработка CancellationException
- **Type Converters** — Room TypeConverters для сложных типов (Category, List)


<div style="display: flex; justify-content: center; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/02a3b1a8-9443-47f9-b683-537252690e94"  width="40%" />
  <img src="https://github.com/user-attachments/assets/c8c0a375-38fd-46bd-9831-6105865817d1"  width="40%"/>
  <img src="https://github.com/user-attachments/assets/c82ae373-2049-43b4-8e66-8f7b80c97c4d" width="40%" />
  <img src="https://github.com/user-attachments/assets/076f805c-d07b-41af-ba4f-a9d347d5d9db" width="40%" />
</div>
