package dev.andre.vkeducation.presentation.domain.model

enum class Category(val value: String) {
    PRODUCTIVITY("Производительность"),
    HEALTH_AND_FITNESS("Здоровье и фитнес"),
    PHOTO_AND_VIDEO("Фото и видео"),
    FOOD_AND_DRINKS("Еда и напитки"),
    EDUCATION("Образование"),
    LIFESTYLE("Образ жизни"),
    SHOPPING("Шопинг"),
    NEWS("Новости"),
    MUSIC("Музыка"),
    GAMES("Игры"),
    FINANCE("Финансы"),
    UTILITIES("Утилиты"),
    NAVIGATION("Навигация"),
    COMMUNICATION("Общение"),
    BUSINESS("Бизнес"),
    WEATHER("Погода"),
    ENTERTAINMENT("Развлечения"),
    BOOKS_AND_REFERENCE("Книги и справочники"),
    UNKNOWN("Unknown");

    companion object {
        fun from(value: String): Category {
            val normalized = value.trim().lowercase()

            return entries.firstOrNull {
                it.value.trim().lowercase() == normalized
            } ?: UNKNOWN
        }
    }
}