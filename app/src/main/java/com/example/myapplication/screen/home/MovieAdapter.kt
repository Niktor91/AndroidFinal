package com.example.myapplication.screen.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemMovieBinding
import com.example.myapplication.screen.home.model.Movie
import java.util.Locale

class MovieAdapter(
    private val onMovieClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val movies = mutableListOf<Movie>()

    fun submitList(newMovies: List<Movie>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            with(binding) {
                tvTitle.text = movie.title

                val ratingValue = movie.voteAverage ?: 0.0

                ratingBar.rating = (ratingValue / 2).toFloat()

                tvRating.text = String.format(
                    Locale.US,
                    "(%.1f)",
                    ratingValue
                )

                Glide.with(ivMovie.context)
                    .load(BuildConfig.IMAGE_BASE_URL + movie.posterPath)
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.ic_image)
                    .centerCrop()
                    .into(ivMovie)

                root.setOnClickListener {
                    onMovieClick(movie)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size
}