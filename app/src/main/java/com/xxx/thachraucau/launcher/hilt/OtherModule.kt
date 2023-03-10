package com.xxx.thachraucau.launcher.hilt

import com.xxx.thachraucau.launcher.manager.GridManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class OtherModule {

    @Provides
    @Singleton
    fun provideCurentGird() = GridManager.getInstance().currentGrid
}