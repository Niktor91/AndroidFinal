package com.example.myapplication.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.MovieRepository
import com.example.myapplication.screen.home.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository = MovieRepository()) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies = _movies.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun fetchMovies() {
        viewModelScope.launch {
            try {
                val response = repository.getMovies()
                if (response.isSuccessful) {
                    _movies.value = response.body()?.results ?: emptyList()
                } else {
                    _error.value = "Error fetching movies"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
            }
        }
    }
}
