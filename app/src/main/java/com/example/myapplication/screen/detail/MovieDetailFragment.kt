package com.example.myapplication.screen.detail

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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

    override fun init() {
        setupRecycler()
        getDetails()
        getCast()

    }

    private fun getDetails() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    val response =
                        withContext(Dispatchers.IO) {
                            RetrofitProvider.movieService.getMovieDetails(args.movieId)
                        }
                    if (response.isSuccessful) {
                        val data = response.body()!!
                        setupScreen(data)

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
                .error(R.drawable.ic_image)
                .into(ivMovieBackdrop)

            genreAdapter.submitList(movie.genres)

        }
    }

    private fun getCast() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    val response =
                        withContext(Dispatchers.IO) {
                            RetrofitProvider.movieService.getMovieCredits(args.movieId)
                        }
                    if (response.isSuccessful) {
                        val data = response.body()!!
                        crewAdapter.submitList(data.cast)

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
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvPeople.adapter = crewAdapter

        binding.rvGenre.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvGenre.adapter = genreAdapter


    }


}