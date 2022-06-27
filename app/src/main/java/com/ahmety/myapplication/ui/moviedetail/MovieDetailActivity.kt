package com.ahmety.myapplication.ui.moviedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.ahmety.myapplication.R
import com.ahmety.myapplication.databinding.ActivityMovieDetailBinding
import com.ahmety.myapplication.model.Data
import com.ahmety.myapplication.ui.moviedetail.adapter.VideosAdapter
import com.ahmety.myapplication.ui.viewmodel.MoviePersonDetailViewModel
import com.ahmety.myapplication.util.Resource
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    private val movieDetailViewModel: MoviePersonDetailViewModel by viewModels()
    private lateinit var binding: ActivityMovieDetailBinding
    private var adapter: VideosAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        movieDetailViewModel.movieData = intent.getSerializableExtra("data") as Data
        movieDetailViewModel.getMovieVideos()
        setUI()
        observeMovieDetailList()
        setupOnClickListener()
    }

    private fun setUI() {
        binding.apply {
            Glide.with(imgMoviePoster).load("https://image.tmdb.org/t/p/w500" + movieDetailViewModel.movieData?.poster_path)
                .error(
                    R.drawable.ic_baseline_warning_24
                )
                .into(imgMoviePoster)
            textViewMovieTitle.text = movieDetailViewModel.movieData?.title
            textViewMovieSummary.text = movieDetailViewModel.movieData?.overview
        }
    }

    private fun observeMovieDetailList() {
        adapter = VideosAdapter()
        binding.recyclerViewVideos.adapter = adapter
        movieDetailViewModel.videoList.observe(this) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    adapter?.submitList(it.data?.results)
                }
                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    it.message?.let { message ->
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        }
    }

    private fun setupOnClickListener() {
        binding.viewBackBtnArea.setOnClickListener {
            onBackPressed()
        }
    }
}