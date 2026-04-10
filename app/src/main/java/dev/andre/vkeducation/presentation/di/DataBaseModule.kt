package dev.andre.vkeducation.presentation.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.andre.vkeducation.presentation.data.local.AppDatabase
import dev.andre.vkeducation.presentation.data.local.appcatalog.AppCatalogDao
import dev.andre.vkeducation.presentation.data.local.appdetails.AppDetailsDao

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    fun provideAppDatabase(app: Application) : AppDatabase{
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideAppDetailsDao(appDatabase: AppDatabase) : AppDetailsDao {
        return appDatabase.appDetailsDao()
    }

    @Provides
    fun provideAppCatalogDao(appDatabase: AppDatabase) : AppCatalogDao {
        return appDatabase.appCatalogDao()
    }
}