package com.example.myapplication.screen.detail.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCast(
    val cast: List<CastMember>
)

@Serializable
data class CastMember(
    val gender: Int,
    val id: Int,
    val name: String,
    val popularity: Double,
    @SerialName("profile_path")
    val profilePath: String?,
    val character: String,
    val order: Int
)