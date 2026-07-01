package com.example.myapplication.screen.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.myapplication.databinding.ItemGenreBinding
import com.example.myapplication.screen.detail.model.Genre

class GenreAdapter : ListAdapter<Genre, GenreAdapter.GenreViewHolder>(GenreDiffUtil()) {

    inner class GenreViewHolder(private val binding: ItemGenreBinding) : ViewHolder(binding.root) {
        fun bind() {
            val genre = getItem(adapterPosition)
            binding.tvGenre.text = genre.name

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind()
    }
}

private class GenreDiffUtil : DiffUtil.ItemCallback<Genre>() {
    override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
        return oldItem == newItem
    }
}