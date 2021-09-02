package com.cropcircle.photocircle.di

import android.app.Application
import androidx.room.Room
import com.cropcircle.photocircle.local.PhotoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun providePhotoDatabase(application: Application, callback: PhotoDatabase.Callback) =
        Room.databaseBuilder(application, PhotoDatabase::class.java, "photo.db")
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()

    @Provides
    @Singleton
    fun providePhotoDao(database: PhotoDatabase) =
        database.photoDao()

    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}