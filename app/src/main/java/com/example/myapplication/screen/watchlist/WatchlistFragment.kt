package com.example.myapplication.screen.watchlist

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentWatchlistBinding
import com.example.myapplication.screen.home.MovieAdapter

class WatchlistFragment : BaseFragment<FragmentWatchlistBinding>(FragmentWatchlistBinding::inflate) {

    private val watchlistService by lazy { WatchlistService(requireContext()) }
    
    private val movieAdapter by lazy {
        MovieAdapter(onMovieClick = { movie ->
            val action = WatchlistFragmentDirections.actionWatchlistFragmentToMovieDetailFragment(movie.id)
            findNavController().navigate(action)
        })
    }

    override fun init() {
        setupRecycler()
        loadWatchlist()
    }

    private fun setupRecycler() {
        binding.rvWatchlist.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvWatchlist.adapter = movieAdapter
    }

    private fun loadWatchlist() {
        val movies = watchlistService.getWatchlist()
        movieAdapter.submitList(movies)
        binding.tvEmpty.isVisible = movies.isEmpty()
        binding.rvWatchlist.isVisible = movies.isNotEmpty()
    }

    override fun onResume() {
        super.onResume()
        loadWatchlist()
    }
}
