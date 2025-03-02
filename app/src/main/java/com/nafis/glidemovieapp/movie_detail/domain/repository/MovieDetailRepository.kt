package com.nafis.glidemovieapp.movie_detail.domain.repository

import com.nafis.glidemovieapp.movie.domain.models.Movie
import com.nafis.glidemovieapp.movie_detail.domain.models.MovieDetail
import com.nafis.glidemovieapp.utils.Response
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    fun fetchMovieDetail(movieId: Int): Flow<Response<MovieDetail>>
    fun fetchMovie(): Flow<Response<List<Movie>>>
}