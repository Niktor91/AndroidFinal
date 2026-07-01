package com.example.myapplication.repository

import com.example.myapplication.retrofit.MovieService
import com.example.myapplication.retrofit.RetrofitProvider

class MovieRepository(private val movieService: MovieService = RetrofitProvider.movieService) {
    suspend fun getMovies() = movieService.getMovies()
    suspend fun getMovieDetails(movieId: Int) = movieService.getMovieDetails(movieId)
    suspend fun getMovieCredits(movieId: Int) = movieService.getMovieCredits(movieId)
}
