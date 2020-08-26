package com.voak.android.tmdbmovies.ui.bottomnavigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.voak.android.tmdbmovies.R
import com.voak.android.tmdbmovies.databinding.FragmentHomeBinding
import com.voak.android.tmdbmovies.ui.details.DetailsActivity
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val homeViewModel = ViewModelProvider(this, viewModelFactory)
            .get(HomeViewModel::class.java)

        binding = FragmentHomeBinding.bind(view).apply {
            viewModel = homeViewModel
            lifecycleOwner = this@HomeFragment
        }

        initRecyclerViews()
        bindViews()

        return binding?.root
    }

    private fun bindViews() {
        binding?.viewModel?.getLoadingLiveData()?.observe(viewLifecycleOwner) {
            if (it) {
                binding?.homeContentLayout?.visibility = View.GONE
                binding?.homeLoading?.visibility = View.VISIBLE
            } else {
                binding?.homeLoading?.visibility = View.GONE
                binding?.homeContentLayout?.visibility = View.VISIBLE
            }
        }

        binding?.viewModel?.getNowPlayingMoviesLiveData()?.observe(viewLifecycleOwner) {
            (binding?.cinemaRecyclerView?.adapter as NowPlayingMovieAdapter).setMovies(it)
        }

        binding?.viewModel?.getTvShowsOnTheAirLiveData()?.observe(viewLifecycleOwner) {
            (binding?.airTvRecyclerView?.adapter as TvAirAdapter).setTvShows(it)
        }

        binding?.viewModel?.getPopularMoviesLiveData()?.observe(viewLifecycleOwner) {
            (binding?.popularMoviesRecyclerView?.adapter as PopularMoviesAdapter).setMovies(it)
        }

        binding?.viewModel?.onAttached()
    }

    private fun initRecyclerViews() {
        binding?.cinemaRecyclerView?.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        binding?.cinemaRecyclerView?.adapter = NowPlayingMovieAdapter {
            navigateToMovieDetailsCallback(it)
        }

        binding?.airTvRecyclerView?.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        binding?.airTvRecyclerView?.adapter = TvAirAdapter() {
            navigateToTvShowDetailsCallback(it)
        }

        binding?.popularMoviesRecyclerView?.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        binding?.popularMoviesRecyclerView?.adapter = PopularMoviesAdapter() {
            navigateToMovieDetailsCallback(it)
        }
    }

    private fun navigateToMovieDetailsCallback(id: Int) {
        val intent = DetailsActivity.instance(DetailsActivity.MOVIE_FRAGMENT, id, requireContext())
        startActivity(intent)
    }

    private fun navigateToTvShowDetailsCallback(id: Int) {
        val intent = DetailsActivity.instance(DetailsActivity.TV_SHOW_FRAGMENT, id, requireContext())
        startActivity(intent)
    }
}