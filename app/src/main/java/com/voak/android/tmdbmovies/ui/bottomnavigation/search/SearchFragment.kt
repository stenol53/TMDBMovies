package com.voak.android.tmdbmovies.ui.bottomnavigation.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.voak.android.tmdbmovies.R
import com.voak.android.tmdbmovies.databinding.FragmentSearchBinding
import com.voak.android.tmdbmovies.ui.bottomnavigation.home.PopularMoviesAdapter
import com.voak.android.tmdbmovies.ui.bottomnavigation.home.TvAirAdapter
import com.voak.android.tmdbmovies.ui.details.DetailsActivity
import com.voak.android.tmdbmovies.ui.details.movie.MovieDetailsFragment
import com.voak.android.tmdbmovies.utils.SharedPreferences
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SearchFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private var binding: FragmentSearchBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        binding = FragmentSearchBinding.bind(view)
        binding?.viewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)


        initViews()
        bindViews()

        return binding?.root
    }

    private fun initViews() {
        binding?.moviesRecycler?.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }

        binding?.moviesRecycler?.adapter = PopularMoviesAdapter({
            val intent = DetailsActivity.instance(DetailsActivity.MOVIE_FRAGMENT, it, requireContext())
            startActivity(intent)
        })

        binding?.moviesRecycler?.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastPos: Int = (recyclerView.layoutManager as LinearLayoutManager)
                    .findLastVisibleItemPosition()
                val itemsCount: Int = (binding?.moviesRecycler?.adapter as PopularMoviesAdapter).itemCount

                if (dx > 0 && lastPos >= itemsCount - 3) {
                    binding?.viewModel?.updateMovies()
                }
            }
        })

        binding?.tvShowsRecycler?.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }

        binding?.tvShowsRecycler?.adapter = TvAirAdapter({
            val intent = DetailsActivity.instance(DetailsActivity.TV_SHOW_FRAGMENT, it, requireContext())
            startActivity(intent)
        })

        binding?.tvShowsRecycler?.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastPos: Int = (recyclerView.layoutManager as LinearLayoutManager)
                    .findLastVisibleItemPosition()
                val itemsCount: Int = (binding?.tvShowsRecycler?.adapter as TvAirAdapter).itemCount

                if (dx > 0 && lastPos >= itemsCount - 3) {
                    binding?.viewModel?.updateTv()
                }
            }
        })
    }

    private fun bindViews() {
        binding?.viewModel?.getProgressLiveData()?.observe(viewLifecycleOwner) {
            if (it) {
                binding?.searchProgress?.visibility = View.VISIBLE
                binding?.searchContent?.visibility = View.GONE
            } else {
                binding?.searchProgress?.visibility = View.GONE
                binding?.searchContent?.visibility = View.VISIBLE
            }
        }

        binding?.viewModel?.getMoviesLiveData()?.observe(viewLifecycleOwner) {
            (binding?.moviesRecycler?.adapter as? PopularMoviesAdapter)?.setMovies(it)
        }

        binding?.viewModel?.getTvShowsLiveData()?.observe(viewLifecycleOwner) {
            (binding?.tvShowsRecycler?.adapter as? TvAirAdapter)?.setTvShows(it)
        }

        binding?.viewModel?.getMoviesTotalResultsLiveData()?.observe(viewLifecycleOwner) {
            binding?.moviesCount?.text = getString(R.string.search_movies_count, it)
        }

        binding?.viewModel?.getTvTotalResultsLiveData()?.observe(viewLifecycleOwner) {
            binding?.tvShowsCount?.text = getString(R.string.search_tv_count, it)
        }

        binding?.searchView?.let { binding?.viewModel?.bindSearchView(it) }
        binding?.searchView?.setText(SharedPreferences.getSearchQuery(requireContext()))
    }
}