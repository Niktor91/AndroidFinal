package com.example.myapplication.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.MovieRepository
import com.example.myapplication.screen.detail.model.MovieCast
import com.example.myapplication.screen.detail.model.MovieDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val repository: MovieRepository = MovieRepository()) : ViewModel() {

    private val _movieDetail = MutableStateFlow<MovieDetail?>(null)
    val movieDetail = _movieDetail.asStateFlow()

    private val _movieCast = MutableStateFlow<MovieCast?>(null)
    val movieCast = _movieCast.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                val detailResponse = repository.getMovieDetails(movieId)
                if (detailResponse.isSuccessful) {
                    _movieDetail.value = detailResponse.body()
                } else {
                    _error.value = "Error fetching details"
                }

                val castResponse = repository.getMovieCredits(movieId)
                if (castResponse.isSuccessful) {
                    _movieCast.value = castResponse.body()
                } else {
                    _error.value = "Error fetching cast"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
            }
        }
    }
}
