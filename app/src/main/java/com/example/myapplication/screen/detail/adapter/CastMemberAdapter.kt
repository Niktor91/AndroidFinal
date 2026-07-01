package com.example.myapplication.screen.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemCastBinding
import com.example.myapplication.screen.detail.model.CastMember

class CastMemberAdapter :
    ListAdapter<CastMember, CastMemberAdapter.CastMemberViewHolder>(CastMemberDiffUtil()) {

    inner class CastMemberViewHolder(private val binding: ItemCastBinding) :
        ViewHolder(binding.root) {
        fun bind() {
            val member = getItem(adapterPosition)
            Glide.with(binding.root)
                .load(BuildConfig.IMAGE_BASE_URL + member.profilePath)
                .error(R.drawable.ic_image)
                .into(binding.ivCast)
            binding.tvMemberName.text = member.name
            binding.tvCharacterName.text = member.character

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastMemberViewHolder {
        val binding = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastMemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastMemberViewHolder, position: Int) {
        holder.bind()
    }
}

private class CastMemberDiffUtil : DiffUtil.ItemCallback<CastMember>() {
    override fun areItemsTheSame(oldItem: CastMember, newItem: CastMember): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CastMember, newItem: CastMember): Boolean {
        return oldItem == newItem
    }


}