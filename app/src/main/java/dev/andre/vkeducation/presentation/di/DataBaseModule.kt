package dev.andre.vkeducation.presentation.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.andre.vkeducation.presentation.data.local.AppDatabase
import dev.andre.vkeducation.presentation.data.local.appcatalog.AppCatalogDao
import dev.andre.vkeducation.presentation.data.local.appdetails.AppDetailsDao
import dev.andre.vkeducation.presentation.data.local.downloads.DownloadsListDao
import dev.andre.vkeducation.presentation.data.local.wishlist.WishListDao

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    private val migration1To2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS downloads_list (id TEXT PRIMARY KEY NOT NULL)"
            )
        }
    }

    @Provides
    fun provideAppDatabase(app: Application) : AppDatabase{
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).addMigrations(migration1To2).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideAppDetailsDao(appDatabase: AppDatabase) : AppDetailsDao {
        return appDatabase.appDetailsDao()
    }

    @Provides
    fun provideAppCatalogDao(appDatabase: AppDatabase) : AppCatalogDao {
        return appDatabase.appCatalogDao()
    }

    @Provides
    fun provideWishListDao(appDatabase: AppDatabase) : WishListDao {
        return appDatabase.wishListDao()
    }

    @Provides
    fun provideDownloadsListDao(appDatabase: AppDatabase) : DownloadsListDao {
        return appDatabase.downloadslistDao()
    }
}