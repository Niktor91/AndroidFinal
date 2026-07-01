package com.example.myapplication.screen.detail.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetail(
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String?,
    val id: Int,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String?,
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("release_date")
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int?,
    val status: String,
    val title: String,
    val video: Boolean,
    @SerialName("vote_average")
    val voteAverage: Double
)

@Serializable
data class Genre(
    val id: Int,
    val name: String
)
