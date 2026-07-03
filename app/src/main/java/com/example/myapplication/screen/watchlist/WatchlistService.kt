package com.example.myapplication.screen.watchlist

import android.content.Context
import com.example.myapplication.screen.home.model.Movie
import com.google.firebase.auth.FirebaseAuth
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class WatchlistService(context: Context) {
    private val prefs = context.getSharedPreferences("watchlist_prefs", Context.MODE_PRIVATE)
    private val auth = FirebaseAuth.getInstance()
    private val json = Json { ignoreUnknownKeys = true }

    private val userId: String?
        get() = auth.currentUser?.uid

    fun addToWatchlist(movie: Movie) {
        val uid = userId ?: return
        val currentList = getWatchlist().toMutableList()
        if (currentList.none { it.id == movie.id }) {
            currentList.add(movie)
            saveWatchlist(uid, currentList)
        }
    }

    fun removeFromWatchlist(movieId: Int) {
        val uid = userId ?: return
        val currentList = getWatchlist().toMutableList()
        currentList.removeAll { it.id == movieId }
        saveWatchlist(uid, currentList)
    }

    fun isInWatchlist(movieId: Int): Boolean {
        return getWatchlist().any { it.id == movieId }
    }

    fun getWatchlist(): List<Movie> {
        val uid = userId ?: return emptyList()
        val jsonString = prefs.getString("watchlist_$uid", null) ?: return emptyList()
        return try {
            json.decodeFromString<List<Movie>>(jsonString)
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun saveWatchlist(uid: String, list: List<Movie>) {
        val jsonString = json.encodeToString(list)
        prefs.edit().putString("watchlist_$uid", jsonString).apply()
    }
}
