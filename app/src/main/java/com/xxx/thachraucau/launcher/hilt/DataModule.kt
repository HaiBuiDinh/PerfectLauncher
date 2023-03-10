package com.xxx.thachraucau.launcher.hilt

import android.content.Context
import com.xxx.thachraucau.launcher.repository.AppDataSource
import com.xxx.thachraucau.launcher.repository.AppLibraryRepository
import com.xxx.thachraucau.launcher.repository.AppRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Reusable
    @Provides
    fun provideAppDataSource(@ApplicationContext appContext: Context) = AppDataSource(appContext)

    @Reusable
    @Provides
    fun provideAppRepository(appDataSource: AppDataSource) = AppRepository(appDataSource)

    @Reusable
    @Provides
    fun provideAppLibraryRepository(appDataSource: AppDataSource) = AppLibraryRepository(appDataSource)
}