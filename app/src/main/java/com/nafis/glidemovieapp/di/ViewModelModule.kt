package com.nafis.glidemovieapp.di

import com.nafis.glidemovieapp.movie.domain.repository.MovieRepository
import com.nafis.glidemovieapp.ui.home.HomeViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {
    @Provides
    @Singleton
    fun provideHomeViewModel(repository: MovieRepository): HomeViewModel {
        return HomeViewModel(repository)
    }
}