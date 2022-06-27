package com.ahmety.myapplication.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.ahmety.myapplication.databinding.ActivityMainBinding
import com.ahmety.myapplication.model.Data
import com.ahmety.myapplication.model.MediaType
import com.ahmety.myapplication.ui.main.adapter.MainAdapter
import com.ahmety.myapplication.ui.moviedetail.MovieDetailActivity
import com.ahmety.myapplication.util.Resource
import com.ahmety.myapplication.ui.main.viewmodel.MainViewModel
import com.ahmety.myapplication.ui.persondetail.PersonDetailActivity
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var adapter: MainAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mainViewModel.getPopularMovies()
        setupOnClickListener()
        observeSearchText()
        observeMovieList()
    }

    private fun setupOnClickListener() {
        binding.apply {
            searchEditText.setOnClickListener {
            }
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> {
                            mainViewModel.searchQueryKeywords(MediaType.Movie, binding.searchEditText.text.toString())
                        }
                        1 -> {
                            mainViewModel.searchQueryKeywords(MediaType.Person, binding.searchEditText.text.toString())
                        }
                        else -> {
                            mainViewModel.searchQueryKeywords(MediaType.Tv, binding.searchEditText.text.toString())
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
        }
    }

    private fun observeSearchText() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.toString() != "") {
                    binding.tabLayout.isVisible = true
                    binding.textViewTitle.isInvisible = true
                    when (binding.tabLayout.selectedTabPosition) {
                        0 -> {
                            mainViewModel.searchQueryKeywords(MediaType.Movie, s.toString())
                        }
                        1 -> {
                            mainViewModel.searchQueryKeywords(MediaType.Person, s.toString())
                        }
                        else -> {
                            mainViewModel.searchQueryKeywords(MediaType.Tv, s.toString())
                        }
                    }
                } else {
                    binding.tabLayout.isVisible = false
                    binding.textViewTitle.isInvisible = false
                }
            }
        })
    }

    private fun observeMovieList() {
        adapter = MainAdapter(::onClickMovie)
        binding.recyclerViewMain.adapter = adapter
        mainViewModel.movieList.observe(this) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBarMain.isVisible = false
                    if (it.data?.results?.get(0)?.media_type == null){
                        adapter?.submitList(it.data?.results)
                    } else {
                        adapter?.submitList(it.data.results.filter { listFiltered -> listFiltered.media_type == it.mediaType?.mediaType })
                        binding.textViewNotFound.isVisible =
                            it.data.results.none { listFiltered -> listFiltered.media_type == it.mediaType?.mediaType }
                    }
                }
                is Resource.Error -> {
                    binding.progressBarMain.isVisible = false
                    it.message?.let { message ->
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    binding.progressBarMain.isVisible = true
                }
            }

        }
    }

    private fun onClickMovie(data: Data) {
        when (data.media_type) {
            MediaType.Movie.mediaType -> {
                val intent = Intent(this@MainActivity, MovieDetailActivity::class.java)
                goToNextActivity(intent, data)
            }
            MediaType.Person.mediaType -> {
                val intent = Intent(this@MainActivity, PersonDetailActivity::class.java)
                goToNextActivity(intent, data)
            }
            else -> {
                val intent = Intent(this@MainActivity, MovieDetailActivity::class.java)
                goToNextActivity(intent, data)
            }
        }
    }

    private fun goToNextActivity(intent: Intent, data: Data) {
        intent.putExtra("data", data as Serializable)
        startActivity(intent)
    }

}