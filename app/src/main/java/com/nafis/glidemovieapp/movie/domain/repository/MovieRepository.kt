package com.nafis.glidemovieapp.movie.domain.repository

import com.nafis.glidemovieapp.movie.domain.models.Movie
import com.nafis.glidemovieapp.utils.Response
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun fetchDiscoverMovie(): Flow<Response<List<Movie>>>
    fun fetchTrendingMovie(): Flow<Response<List<Movie>>>

}