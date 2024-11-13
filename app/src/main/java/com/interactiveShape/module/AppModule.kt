package com.interactiveShape.module


import com.interactiveShape.viewModel.ShapeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Singleton
    @Provides
    fun provideHomeViewModel(): ShapeViewModel {
        return ShapeViewModel()
    }
}
