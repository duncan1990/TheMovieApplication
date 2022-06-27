package com.ahmety.myapplication.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmety.myapplication.R
import com.ahmety.myapplication.databinding.ItemMovieBinding
import com.ahmety.myapplication.model.Data
import com.ahmety.myapplication.model.MediaType
import com.bumptech.glide.Glide

class MainAdapter(private val onClickItem: (Data) -> Unit) : ListAdapter<Data, MainAdapter.MovieViewHolder>(
    MainAdapterComparator
) {
    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(item: Data, onClickItem: (Data) -> Unit) {
            binding.apply {
                when (item.media_type) {
                    MediaType.Tv.mediaType -> {
                        Glide.with(imageViewMovie).load("https://image.tmdb.org/t/p/w500" + item.poster_path).error(R.drawable.ic_baseline_warning_24).into(imageViewMovie)
                        textViewMName.text = item.name
                        textViewMSummary.text = item.overview
                    }
                    MediaType.Person.mediaType -> {
                        Glide.with(imageViewMovie).load("https://image.tmdb.org/t/p/w500" + item.profile_path).error(R.drawable.ic_baseline_warning_24).into(imageViewMovie)
                        textViewMName.text = item.name
                        textViewMSummary.text = ""
                    }
                    else -> {
                        Glide.with(imageViewMovie).load("https://image.tmdb.org/t/p/w500" + item.poster_path).error(R.drawable.ic_baseline_warning_24).into(imageViewMovie)
                        textViewMName.text = item.title
                        textViewMSummary.text = item.overview
                    }
                }
                root.setOnClickListener {
                    onClickItem.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val mView = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(mView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position), onClickItem)
    }

    companion object {
        private val MainAdapterComparator = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem == newItem
            }
        }
    }
}