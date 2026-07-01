package com.example.myapplication.screen.home.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieData(
    val results:List<Movie>
)

@Serializable
data class Movie(
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdrop: String?,
    val id: Int,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("vote_average")
    val voteAverage:Double,
    val title: String,
)