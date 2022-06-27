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

class PersonTvAdapter() : ListAdapter<Cast, PersonTvAdapter.PersonTvHolder>(
    PersonTvAdapterComparator
) {
    inner class PersonTvHolder(private val binding: ItemCastBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(item: Cast) {
            binding.apply {
                Glide.with(imageViewCast).load("https://image.tmdb.org/t/p/w500" + item.poster_path).error(R.drawable.ic_baseline_warning_24).into(imageViewCast)
                textViewName.text = item.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonTvHolder {
        val mView = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonTvHolder(mView)
    }

    override fun onBindViewHolder(holder: PersonTvHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val PersonTvAdapterComparator = object : DiffUtil.ItemCallback<Cast>() {
            override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
                return oldItem == newItem
            }
        }
    }
}