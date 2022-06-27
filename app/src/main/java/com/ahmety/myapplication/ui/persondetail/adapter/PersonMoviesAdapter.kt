package com.ahmety.myapplication.ui.persondetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmety.myapplication.R
import com.ahmety.myapplication.databinding.ItemCastBinding
import com.ahmety.myapplication.model.Cast
import com.bumptech.glide.Glide

class PersonMoviesAdapter : ListAdapter<Cast, PersonMoviesAdapter.PersonViewHolder>(
    PersonAdapterComparator
) {
    inner class PersonViewHolder(private val binding: ItemCastBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(item: Cast) {
            binding.apply {
                Glide.with(imageViewCast).load("https://image.tmdb.org/t/p/w500" + item.poster_path).error(R.drawable.ic_baseline_warning_24).into(imageViewCast)
                textViewName.isVisible = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val mView = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonViewHolder(mView)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val PersonAdapterComparator = object : DiffUtil.ItemCallback<Cast>() {
            override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem == newItem
            }
        }
    }
}