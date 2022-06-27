package com.ahmety.myapplication.ui.persondetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.ahmety.myapplication.R
import com.ahmety.myapplication.databinding.ActivityPersonDetailBinding
import com.ahmety.myapplication.model.Data
import com.ahmety.myapplication.ui.persondetail.adapter.PersonMoviesAdapter
import com.ahmety.myapplication.ui.persondetail.adapter.PersonTvAdapter
import com.ahmety.myapplication.ui.viewmodel.MoviePersonDetailViewModel
import com.ahmety.myapplication.util.Resource
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonDetailActivity : AppCompatActivity() {
    private val personDetailViewModel: MoviePersonDetailViewModel by viewModels()
    private lateinit var binding: ActivityPersonDetailBinding
    private var adapterPersonMovies: PersonMoviesAdapter? = null
    private var adapterPersonTv: PersonTvAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        personDetailViewModel.personData = intent.getSerializableExtra("data") as Data
        personDetailViewModel.getPersonDetail()
        personDetailViewModel.getPersonCastMovie()
        personDetailViewModel.getPersonCastTv()
        observePersonDetailList()
        observePersonMoviesCastList()
        observePersonTvCastList()
        setupOnClickListener()
    }

    private fun observePersonDetailList() {
        personDetailViewModel.personDetailList.observe(this) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    binding.textViewName.text = it.data?.name
                    Glide.with(binding.imgAvatar).load("https://image.tmdb.org/t/p/w500" + it.data?.profile_path)
                        .error(
                            R.drawable.ic_baseline_warning_24
                        )
                        .into(binding.imgAvatar)
                    binding.textViewJob.text = it.data?.known_for_department
                    binding.textViewBiography.text = it.data?.biography
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

    private fun observePersonMoviesCastList() {
        adapterPersonMovies = PersonMoviesAdapter()
        binding.recyclerViewMovies.adapter = adapterPersonMovies
        personDetailViewModel.personCastMovieList.observe(this) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    adapterPersonMovies?.submitList(it.data?.cast)
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

    private fun observePersonTvCastList() {
        adapterPersonTv = PersonTvAdapter()
        binding.recyclerViewTv.adapter = adapterPersonTv
        personDetailViewModel.personCastTvList.observe(this) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    adapterPersonTv?.submitList(it.data?.cast)
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