package com.example.myapplication.screen.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemMovieBinding
import com.example.myapplication.screen.home.model.Movie
import java.util.Locale

class MovieAdapter(
    private val onMovieClick:(Int)->Unit
) : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffUtil()) {

    inner class MovieViewHolder(private val binding: ItemMovieBinding) : ViewHolder(binding.root) {
        fun bind() {
            val movie = getItem(adapterPosition)
            Glide.with(binding.root)
                .load(BuildConfig.IMAGE_BASE_URL + movie.posterPath)
                .error(R.drawable.ic_launcher_background)
                .into(binding.ivMovie)
            binding.rating.rating = (movie.voteAverage / 2).toFloat()
            binding.tvRating.text = binding.root.context.getString(
                R.string.rating,
                String.format(Locale.US, "%.1f", movie.voteAverage)
            )
            binding.tvTitle.text = movie.title
            binding.tvOverview.text = movie.overview
            binding.root.setOnClickListener {
                onMovieClick(movie.id)
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind()
    }
}

private class MovieDiffUtil : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}