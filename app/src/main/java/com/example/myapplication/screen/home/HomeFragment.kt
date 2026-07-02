package com.example.myapplication.screen.home

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.retrofit.RetrofitProvider
import com.example.myapplication.util.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.lifecycleScope

class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val navController by lazy { findNavController() }

    private val movieAdapter by lazy {
        MovieAdapter(onMovieClick = { movie ->
            navigateToDetails(movieId = movie.id)
        })
    }

    override fun init() {
        setupRecycler()
        getMovies()
    }

    private fun setupRecycler() {
        binding.rvMovies.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMovies.adapter = movieAdapter
    }

    private fun navigateToDetails(movieId: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(movieId)
        navController.navigate(action)
    }

    private fun getMovies() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitProvider.movieService.getMovies()
                }

                if (response.isSuccessful) {
                    val movies = response.body()?.results ?: emptyList()
                    movieAdapter.submitList(movies)
                } else {
                    showToast("Error")
                }

            } catch (e: Exception) {
                showToast(e.message ?: "Error")
            }
        }
    }
}