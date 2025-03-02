package com.nafis.glidemovieapp.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nafis.glidemovieapp.common.data.ApiMapper
import com.nafis.glidemovieapp.movie.data.remote.api.MovieApiService
import com.nafis.glidemovieapp.movie.data.remote.models.MovieDto
import com.nafis.glidemovieapp.movie.data.repository_impl.MovieRepositoryImpl
import com.nafis.glidemovieapp.movie.domain.models.Movie
import com.nafis.glidemovieapp.movie.domain.repository.MovieRepository
import com.nafis.glidemovieapp.utils.K
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieApiService: MovieApiService,
        @Named("MovieListMapper") mapper: ApiMapper<List<Movie>, MovieDto> // Gunakan @Named
    ): MovieRepository = MovieRepositoryImpl(
        movieApiService, mapper
    )

    @Provides
    @Singleton
    fun provideMovieApiService(): MovieApiService {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(K.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(MovieApiService::class.java)
    }
}