package com.example.myapplication.screen.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemMoviePosterBinding
import com.example.myapplication.screen.home.model.Movie

class MoviePosterAdapter : RecyclerView.Adapter<MoviePosterAdapter.PosterViewHolder>() {

    private val movies = mutableListOf<Movie>()

    fun submitList(newMovies: List<Movie>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        return PosterViewHolder(
            ItemMoviePosterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PosterViewHolder, position: Int) {
        if (movies.isNotEmpty()) {
            holder.bind(movies[position % movies.size])
        }
    }

    override fun getItemCount(): Int = if (movies.isEmpty()) 0 else Int.MAX_VALUE

    class PosterViewHolder(private val binding: ItemMoviePosterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            Glide.with(binding.ivPoster.context)
                .load(BuildConfig.IMAGE_BASE_URL + movie.posterPath)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .centerCrop()
                .into(binding.ivPoster)
        }
    }
}
