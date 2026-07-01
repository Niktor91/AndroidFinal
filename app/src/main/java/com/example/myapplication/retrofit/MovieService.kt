package com.example.myapplication.retrofit


import com.example.myapplication.BuildConfig
import com.example.myapplication.screen.detail.model.MovieCast
import com.example.myapplication.screen.home.model.MovieData
import com.example.myapplication.screen.detail.model.MovieDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface MovieService {
    @GET("discover/movie")
    suspend fun getMovies(@Header("Authorization") bearerToken: String = BuildConfig.API_KEY): Response<MovieData>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId:Int,
        @Header("Authorization") bearerToken: String = BuildConfig.API_KEY
    ): Response<MovieCast>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId:Int,
        @Header("Authorization") bearerToken: String = BuildConfig.API_KEY
    ):Response<MovieDetail>
}