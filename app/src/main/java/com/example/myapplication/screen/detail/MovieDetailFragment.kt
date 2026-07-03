package com.example.myapplication.screen.detail

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentMovieDetailBinding
import com.example.myapplication.retrofit.RetrofitProvider
import com.example.myapplication.screen.detail.adapter.CastMemberAdapter
import com.example.myapplication.screen.detail.adapter.GenreAdapter
import com.example.myapplication.screen.detail.model.MovieDetail
import com.example.myapplication.screen.home.model.Movie
import com.example.myapplication.screen.watchlist.WatchlistService
import com.example.myapplication.util.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {

    private val crewAdapter by lazy { CastMemberAdapter() }
    private val genreAdapter by lazy { GenreAdapter() }
    private val args by navArgs<MovieDetailFragmentArgs>()
    private val watchlistService by lazy { WatchlistService(requireContext()) }
    private var currentMovie: MovieDetail? = null

    override fun init() {
        setupBackButton()
        setupWatchlistButton()
        setupRecycler()
        getDetails()
        getCast()
    }

    private fun setupWatchlistButton() {
        binding.btnAddToWatchlist.setOnClickListener {
            currentMovie?.let { movie ->
                if (watchlistService.isInWatchlist(movie.id)) {
                    watchlistService.removeFromWatchlist(movie.id)
                    showToast(getString(R.string.remove_from_watchlist))
                } else {
                    watchlistService.addToWatchlist(
                        Movie(
                            adult = movie.adult,
                            backdrop = movie.backdropPath,
                            id = movie.id,
                            originalLanguage = "en",
                            originalTitle = movie.originalTitle,
                            overview = movie.overview ?: "",
                            popularity = movie.popularity,
                            posterPath = movie.posterPath,
                            releaseDate = movie.releaseDate,
                            voteAverage = movie.voteAverage,
                            title = movie.title
                        )
                    )
                    showToast(getString(R.string.add_to_watchlist))
                }
                updateWatchlistButton()
            }
        }
    }

    private fun updateWatchlistButton() {
        currentMovie?.let {
            val isInWatchlist = watchlistService.isInWatchlist(it.id)
            binding.btnAddToWatchlist.text = if (isInWatchlist) {
                getString(R.string.remove_from_watchlist)
            } else {
                getString(R.string.add_to_watchlist)
            }
            binding.btnAddToWatchlist.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    if (isInWatchlist) R.color.button_secondary_bg else R.color.movie_accent
                )
            )
        }
    }

    private fun setupBackButton() {
        binding.btnBack.setOnClickListener {
            val navController = findNavController()

            val popped = navController.popBackStack(
                R.id.homeFragment,
                false
            )

            if (!popped) {
                navController.navigate(R.id.homeFragment)
            }
        }
    }

    private fun getDetails() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    val response = withContext(Dispatchers.IO) {
                        RetrofitProvider.movieService.getMovieDetails(args.movieId)
                    }

                    if (response.isSuccessful) {
                        val data = response.body()

                        if (data != null) {
                            currentMovie = data
                            setupScreen(data)
                            updateWatchlistButton()
                        } else {
                            showToast(getString(R.string.error))
                        }
                    } else {
                        showToast(getString(R.string.error))
                    }

                } catch (e: Exception) {
                    showToast(e.message ?: getString(R.string.error))
                }
            }
        }
    }

    private fun setupScreen(movie: MovieDetail) {
        with(binding) {
            tvMovieName.text = movie.title
            tvTitle.text = movie.title
            tvOverview.text = movie.overview

            ratingBar.rating = (movie.voteAverage / 2).toFloat()

            ratingValue.text = getString(
                R.string.rating,
                String.format(Locale.US, "%.1f", movie.voteAverage)
            )

            Glide.with(this@MovieDetailFragment)
                .load(BuildConfig.IMAGE_BASE_URL + movie.backdropPath)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .centerCrop()
                .into(ivMovieBackdrop)

            genreAdapter.submitList(movie.genres)
        }
    }

    private fun getCast() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    val response = withContext(Dispatchers.IO) {
                        RetrofitProvider.movieService.getMovieCredits(args.movieId)
                    }

                    if (response.isSuccessful) {
                        val data = response.body()
                        crewAdapter.submitList(data?.cast ?: emptyList())
                    } else {
                        showToast(getString(R.string.error))
                    }

                } catch (e: Exception) {
                    showToast(e.message ?: getString(R.string.error))
                }
            }
        }
    }

    private fun setupRecycler() {
        binding.rvPeople.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        binding.rvPeople.adapter = crewAdapter

        binding.rvGenre.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        binding.rvGenre.adapter = genreAdapter
    }
}