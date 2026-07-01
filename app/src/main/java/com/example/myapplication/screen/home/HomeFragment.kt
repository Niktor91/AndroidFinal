package com.example.myapplication.screen.home

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.retrofit.RetrofitProvider
import com.example.myapplication.util.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val navController by lazy { findNavController() }
    private val movieAdapter by lazy {
        MovieAdapter(onMovieClick = {
            navigateToDetails(it)
        })
    }


    override fun init() {
        getMovies()
        setupRecycler()

    }

    private fun setupRecycler() {
        binding.rvMovies.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvMovies.adapter = movieAdapter
    }

    private fun getMovies() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    val moviesCall =
                        withContext(Dispatchers.IO) { RetrofitProvider.movieService.getMovies() }
                    if (moviesCall.isSuccessful) {
                        val movies = moviesCall.body()!!
                        movieAdapter.submitList(movies.results)

                    } else {
                        showToast(getString(R.string.error))
                    }

                } catch (e: Exception) {
                    showToast(e.message ?: getString(R.string.error))
                }

            }
        }
    }

    private fun navigateToDetails(movieId: Int) {
        navController.navigate(
            HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(
                movieId
            )
        )
    }


}