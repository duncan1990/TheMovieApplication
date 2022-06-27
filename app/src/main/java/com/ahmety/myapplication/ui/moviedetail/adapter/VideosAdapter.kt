package com.ahmety.myapplication.ui.moviedetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmety.myapplication.databinding.ItemVideosBinding
import com.ahmety.myapplication.model.MovieVideoResult

class VideosAdapter : ListAdapter<MovieVideoResult, VideosAdapter.VideosViewHolder>(
    VideosAdapterComparator
) {
    inner class VideosViewHolder(private val binding: ItemVideosBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(item: MovieVideoResult) {
            binding.apply {
                textViewTitle.text = item.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {
        val mView = ItemVideosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideosViewHolder(mView)
    }

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val VideosAdapterComparator = object : DiffUtil.ItemCallback<MovieVideoResult>() {
            override fun areItemsTheSame(oldItem: MovieVideoResult, newItem: MovieVideoResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieVideoResult, newItem: MovieVideoResult): Boolean {
                return oldItem == newItem
            }
        }
    }
}