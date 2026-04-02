package dev.andre.vkeducation.presentation.data.api

import dev.andre.vkeducation.presentation.data.dto.AppDetailsDto
import kotlinx.coroutines.delay

class AppDetailsApi{
    suspend fun get(id:String): AppDetailsDto? {
        delay(2000)
        val app = getById(id, getApp())
        return app
    }

    private fun getById(id: String, list: List<AppDetailsDto>): AppDetailsDto?{
        return list.find { it.id == id }
    }

    private fun getApp() = listOf(
        AppDetailsDto(
            id = "1",
            name = "СберБанк Онлайн — с Салютом",
            developer = "Sberbank",
            category = "APP",
            ageRating = 6,
            size = 189.2f,
            screenshotUrlList = listOf(
                "https://static.rustore.ru/imgproxy/YAyMfd1C5Y4ADB-81F4yT0rqc7hoSOkMOyTXTyXkG60/preset:web_scr_prt_162/plain/https://static.rustore.ru/2026/2/19/e2/apk/462271/content/SCREENSHOT/af9920e4-eb9a-45d7-9224-ada17e9fad94.png@webp",
                "https://static.rustore.ru/imgproxy/YAyMfd1C5Y4ADB-81F4yT0rqc7hoSOkMOyTXTyXkG60/preset:web_scr_prt_162/plain/https://static.rustore.ru/2026/2/19/e2/apk/462271/content/SCREENSHOT/af9920e4-eb9a-45d7-9224-ada17e9fad94.png@webp"
            ),
            iconUrl = "https://static.rustore.ru/imgproxy/lQKIdJKRbtJBX0dxbZueqU-a5TEP_-_yKjFjWljOsaE/preset:web_app_icon_62/plain/https://static.rustore.ru/apk/462271/content/ICON/f1b3c68a-b734-48ce-b62f-490208d3fa0e.png@webp",
            description = "Больше чем банк"
        ),

        AppDetailsDto(
            id = "2",
            name = "Яндекс.Браузер — с Алисой",
            developer = "Yandex",
            category = "APP",
            ageRating = 12,
            size = 120.4f,
            screenshotUrlList = listOf(
                "https://static.rustore.ru/imgproxy/R_tObRV6q3c-MKTKMVlJu88Iio7UFjrtOC3GQuSJMrE/preset:web_scr_prt_162/plain/https://static.rustore.ru/2026/1/20/f1/apk/313257919/content/SCREENSHOT/cb6a4a05-0905-4b97-b182-20eab3274d00.jpg@webp",
                "https://static.rustore.ru/imgproxy/BazuNZK9F9jeyaWArRwiQR4CTZ5bz8MlPiL2Na046gg/preset:web_scr_prt_162/plain/https://static.rustore.ru/2026/1/20/bd/apk/313257919/content/SCREENSHOT/d7b2081b-f9ba-471d-a9df-9e7c6336c22d.jpg@webp"
            ),
            iconUrl = "https://static.rustore.ru/imgproxy/U4YtvpK16WhmZanjFxLbq1NabvyjyZJF8iAIFS8pDtw/preset:web_app_icon_160/plain/https://static.rustore.ru/apk/313257919/content/ICON/843c5040-0e09-41bb-958c-b7bacc912c2b.png@webp",
            description = "Быстрый и безопасный браузер"
        ),

        AppDetailsDto(
            id = "3",
            name = "Почта Mail.ru",
            developer = "VK",
            category = "APP",
            ageRating = 12,
            size = 98.5f,
            screenshotUrlList = listOf(
                "https://static.rustore.ru/imgproxy/MQs3o-5t7FMY6518WgSpCbFGZF-H-n1_ql8jqNWH6Es/preset:web_scr_prt_162/plain/https://static.rustore.ru/2026/3/11/45/apk/332223/content/SCREENSHOT/10dea749-03a5-42d7-84f1-ef69c46915ce.png@webp",
                "https://static.rustore.ru/imgproxy/PVXMk77p7BzWEMiJFrs6dtFhKESK8QaFi7tm3QYnX3A/preset:web_scr_prt_162/plain/https://static.rustore.ru/2026/3/11/aa/apk/332223/content/SCREENSHOT/40de5dfa-f822-49df-b613-d0ebd78bd518.png@webp",
                "https://static.rustore.ru/imgproxy/Ffcz9t8LBCAVtEQGCTeKv-vbchC3dxgv6CLAwNoQnr0/preset:web_scr_prt_162/plain/https://static.rustore.ru/2026/3/11/78/apk/332223/content/SCREENSHOT/929a7b92-d82a-4b93-b196-da95ae27c9e2.png@webp"
            ),
            iconUrl = "https://static.rustore.ru/imgproxy/2wnsbc-wCmdbFYEdpH8uL3Jl4db6i7HE9Vj5079oh6Q/preset:web_app_icon_160/plain/https://static.rustore.ru/2026/3/11/7c/apk/332223/content/ICON/2ea61211-2ee2-469b-a08e-acc8a9f3b4c6.png@webp",
            description = "Почтовый клиент для любых ящиков"
        ),

        AppDetailsDto(
            id = "4",
            name = "Яндекс Навигатор",
            developer = "Yandex",
            category = "APP",
            ageRating = 6,
            size = 134.8f,
            screenshotUrlList = listOf(
                "https://static.rustore.ru/imgproxy/TN6h0GZmMbN8iD4C-Me_aY1u0zJAztbmxHixGj4RWzk/preset:web_scr_prt_162/plain/https://static.rustore.ru/2026/3/5/ce/apk/595135/content/SCREENSHOT/d74ff81a-6bbb-41ff-b26a-7c6d468267c2.jpg@webp",
                "https://static.rustore.ru/imgproxy/gdeWlZnQ1g3xq318M4EKsQOueNr1j63Wlf5yxPex5T0/preset:web_scr_prt_162/plain/https://static.rustore.ru/apk/595135/content/SCREENSHOT/916f577b-318c-4ab4-8795-5b8a576c2268.png@webp",
                "https://static.rustore.ru/imgproxy/UlclcZ6985JyyVANl4pce7OL2bixodYoo2A6RUqv_X0/preset:web_scr_prt_162/plain/https://static.rustore.ru/apk/595135/content/SCREENSHOT/1600ea6e-628d-404c-9c4f-a963afcc4e30.png@webp"
            ),
            iconUrl = "https://static.rustore.ru/imgproxy/FvKuW-aUKk34jUz1ZEPXebdfDR0ikU93-JJYC5_Oh4Y/preset:web_app_icon_160/plain/https://static.rustore.ru/apk/595135/content/ICON/32cb5e63-9c59-4280-9a6a-c808113be88f.png@webp",
            description = "Парковки и заправки — по пути"
        ),

        AppDetailsDto(
            id = "5",
            name = "Мой МТС",
            developer = "MTS",
            category = "APP",
            ageRating = 6,
            size = 87.1f,
            screenshotUrlList = listOf(
                "https://static.rustore.ru/imgproxy/xl-HfeCOmitsR6TCh5doWAMQnpjkh7KvuPOG30C3vI8/preset:web_scr_prt_162/plain/https://static.rustore.ru/2025/12/15/ce/apk/336831/content/SCREENSHOT/e3558c77-1ca4-42e2-ad1b-e086fbf1e23a.jpg@webp",
                "https://static.rustore.ru/imgproxy/2IxCqrjbN7PLDHmONt_Xne0GEYO4PmNiy8NDbB5-F2k/preset:web_scr_prt_162/plain/https://static.rustore.ru/2025/12/15/bb/apk/336831/content/SCREENSHOT/dea85320-baab-4b65-9aab-cda9b7e09505.jpg@webp"
            ),
            iconUrl = "https://static.rustore.ru/imgproxy/uAJeOzFbun_tDiquzvvs_kieJ8ihjODiwCb7LGISdos/preset:web_app_icon_160/plain/https://static.rustore.ru/apk/336831/content/ICON/ea6c9e63-bd7f-486f-ac3f-3e9069ecf018.png@webp",
            description = "Центр экосистемы МТС"
        ),

        AppDetailsDto(
            id = "6",
            name = "Яндекс — с Алисой",
            developer = "Yandex",
            category = "APP",
            ageRating = 6,
            size = 102.3f,
            screenshotUrlList = listOf(
                "https://static.rustore.ru/imgproxy/PFLM-GlJd_frNCL5vR_OJZZQhzdL3fvgdvIdpJrVq4A/preset:web_scr_prt_162/plain/https://static.rustore.ru/2025/10/25/6a/apk/579007/content/SCREENSHOT/b14e7901-1fcb-4045-94af-3464c359f224.jpg@webp",
                "https://static.rustore.ru/imgproxy/Au3P2Niy_5KX1We1FiE018ua2_ESFPF4ojmIq8hLrew/preset:web_scr_prt_162/plain/https://static.rustore.ru/2025/10/25/bc/apk/579007/content/SCREENSHOT/eb4422a7-36cf-4d11-a25a-456026f39cc7.jpg@webp"
            ),
            iconUrl = "https://static.rustore.ru/imgproxy/bZNt9jiZUOVXXOG0JdJQleTYIB2cFeE3MaWk7o897jE/preset:web_app_icon_160/plain/https://static.rustore.ru/2025/10/25/1e/apk/579007/content/ICON/939321c0-03f7-484d-9043-c0fb12736ef1.png@webp",
            description = "Поиск всегда под рукой"
        ),
        AppDetailsDto(
            id = "7",
            name = "Гильдия Героев: Экшен ММО РПГ",
            developer = "VK Play",
            category = "GAME",
            ageRating = 12,
            size = 223.7f,
            screenshotUrlList = listOf(
                "https://static.rustore.ru/imgproxy/-y8kd-4B6MQ-1OKbAbnoAIMZAzvoMMG9dSiHMpFaTBc/preset:web_scr_lnd_335/plain/https://static.rustore.ru/apk/393868735/content/SCREENSHOT/dfd33017-e90d-4990-aa8c-6f159d546788.jpg@webp"
            ),
            iconUrl = "https://static.rustore.ru/imgproxy/APsbtHxkVa4MZ0DXjnIkSwFQ_KVIcqHK9o3gHY6pvOQ/preset:web_app_icon_62/plain/https://static.rustore.ru/apk/393868735/content/ICON/3f605e3e-f5b3-434c-af4d-77bc5f38820e.png@webp",
            description = "Легендарный рейд героев в Фэнтези РПГ"
        ),
        AppDetailsDto(
            id = "8",
            name = "World of Tanks Blitz",
            developer = "Wargaming Group",
            category = "GAME",
            ageRating = 12,
            size = 350.5f,
            screenshotUrlList = listOf(
                "https://static.rustore.ru/imgproxy/8Q-70daiXlxERJKq7IUlTacs8grFkbIc3tzxYU3EYiY/preset:web_scr_lnd_335/plain/https://static.rustore.ru/2026/1/19/1b/apk/2063496338/content/SCREENSHOT/3321f386-238c-4d6d-b774-38f7e1cd04a0.jpg@webp",
                "https://static.rustore.ru/imgproxy/X3B72_Xyquh8FYyVnmmR4AwU1tHMO5jTCqJLdaFdBXs/preset:web_scr_lnd_335/plain/https://static.rustore.ru/2026/1/19/5e/apk/2063496338/content/SCREENSHOT/67fd73f6-198c-4ad0-8b72-e88fc62c09c1.jpg@webp"
            ),
            iconUrl = "https://static.rustore.ru/imgproxy/3lFd0OGjC-yV-bX8ysi_vf8za6m_8bYcuwEZ4_PuaU4/preset:web_app_icon_62/plain/https://static.rustore.ru/2026/1/19/19/apk/2063496338/content/ICON/d0b231ac-760b-488d-b311-7376315b0c49.png@webp",
            description = "Танковая ММО экшен-игра"
        ),
        AppDetailsDto(
            id = "9",
            name = "VK",
            developer = "VK",
            category = "APP",
            ageRating = 12,
            size = 95.3f,
            screenshotUrlList = listOf(
                "https://static.rustore.ru/imgproxy/XDU_Q-wv6NXsNWBGG283yMbVgUzFMJkkDNLfbKXKgz0/preset:web_scr_prt_162/plain/https://static.rustore.ru/2026/1/21/fc/apk/317631/content/SCREENSHOT/2752146e-d571-4072-981b-95e2b0e91824.jpg@webp",
                "https://static.rustore.ru/imgproxy/RR3VUiYfALteCUgjF89_X_rapzkyOAIEeeI3ZQgOkCM/preset:web_scr_prt_162/plain/https://static.rustore.ru/2026/1/21/05/apk/317631/content/SCREENSHOT/85eac3e9-3e28-4412-a226-046a8ddaeb5b.jpg@webp",
                "https://static.rustore.ru/imgproxy/naU27n1C9hrbMTuo20_KnnSogEpCSR0cC2-aXMXB23E/preset:web_scr_prt_162/plain/https://static.rustore.ru/2026/1/21/72/apk/317631/content/SCREENSHOT/b1e5ca60-01ab-49d1-aba6-f0174d2f51bf.jpg@webp",
                "https://static.rustore.ru/imgproxy/ERksF-B6Lf8mZFtmhEdqUB2KHjT1oZb7_3KV-DOOqsU/preset:web_scr_prt_162/plain/https://static.rustore.ru/2026/1/21/6f/apk/317631/content/SCREENSHOT/e1c7f559-937d-490e-a801-9479c3fcf89b.jpg@webp",
                "https://static.rustore.ru/imgproxy/arwAx44N4Ctma_wvJW6wJr77WrLpSZUH8QpwSHs44S0/preset:web_scr_prt_162/plain/https://static.rustore.ru/2026/1/21/34/apk/317631/content/SCREENSHOT/7826901d-c604-4c50-a4ce-518291a5cf6c.jpg@webp",
                "https://static.rustore.ru/imgproxy/8oLDy7mCF5HDa3L6gniEw9Vpa4YonLUtoJ0GGN8by5k/preset:web_scr_prt_162/plain/https://static.rustore.ru/2026/1/21/f0/apk/317631/content/SCREENSHOT/32deb56d-7798-4938-a0ba-90fad86364ad.png@webp",
                "https://static.rustore.ru/imgproxy/nixha4tB2GLKovhWcRLGsHGBtoMFaU_kBx89xq6bk-M/preset:web_scr_prt_162/plain/https://static.rustore.ru/2026/1/21/25/apk/317631/content/SCREENSHOT/fac09758-12db-4482-abf6-a540f67afb77.jpg@webp",
                "https://static.rustore.ru/imgproxy/m1IbmOovMAWZJ8UQeygU44m6odFI-HSOTmb1pZ72ekc/preset:web_scr_prt_162/plain/https://static.rustore.ru/2026/1/21/82/apk/317631/content/SCREENSHOT/e217be73-1b2b-4d23-b486-90d0d111538c.jpg@webp",
                "https://static.rustore.ru/imgproxy/snTQ9xbR27GcbHFsMdYzWpOTETNvZjqw_GQLks94UkE/preset:web_scr_prt_162/plain/https://static.rustore.ru/2026/1/21/98/apk/317631/content/SCREENSHOT/0af66e91-1493-4be0-868a-6fe18e44dbe1.jpg@webp"
            ),
            iconUrl = "https://static.rustore.ru/imgproxy/PTo8g-Giv9VHYo7_Rwxw_1wC07KtDM7eSJgAfMlv53s/preset:web_app_icon_160/plain/https://static.rustore.ru/3f3d7180-6eb9-45ad-8706-f467c6dcf82a@webp",
            description = "Социальная сеть"
        )
    )
}