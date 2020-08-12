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
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home.*
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

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerViews()

        binding?.viewModel?.loading?.observe(viewLifecycleOwner) {
            if (it) {
                home_content_layout.visibility = View.GONE
                home_loading_layout.visibility = View.VISIBLE
//                textView.visibility = View.GONE
//                textView2.visibility = View.GONE
//                textView3.visibility = View.GONE
//                more_cinema_movies_text_view.visibility = View.GONE
//                more_popular_text_view.visibility = View.GONE
//                more_tv_air_text_view.visibility = View.GONE
//                cinema_recycler_view.visibility = View.GONE
//                air_tv_recycler_view.visibility = View.GONE
//                popular_movies_recycler_view.visibility = View.GONE
//                home_loading.visibility = View.VISIBLE
            } else {
                home_content_layout.visibility = View.VISIBLE
                home_loading_layout.visibility = View.GONE
//                textView.visibility = View.VISIBLE
//                textView2.visibility = View.VISIBLE
//                textView3.visibility = View.VISIBLE
//                more_cinema_movies_text_view.visibility = View.VISIBLE
//                more_popular_text_view.visibility = View.VISIBLE
//                more_tv_air_text_view.visibility = View.VISIBLE
//                cinema_recycler_view.visibility = View.VISIBLE
//                air_tv_recycler_view.visibility = View.VISIBLE
//                popular_movies_recycler_view.visibility = View.VISIBLE
//                home_loading.visibility = View.GONE
            }
        }

        binding?.viewModel?.nowPlayingMovies?.observe(viewLifecycleOwner) {
            (cinema_recycler_view.adapter as NowPlayingMovieAdapter).setMovies(it)
        }

        binding?.viewModel?.tvShowOnTheAir?.observe(viewLifecycleOwner) {
            (air_tv_recycler_view.adapter as TvAirAdapter).setTvShows(it)
        }

        binding?.viewModel?.popularMovies?.observe(viewLifecycleOwner) {
            (popular_movies_recycler_view.adapter as PopularMoviesAdapter).setMovies(it)
        }

        binding?.viewModel?.onAttached()
    }

    private fun initRecyclerViews() {
        cinema_recycler_view.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        cinema_recycler_view.adapter = NowPlayingMovieAdapter()

        air_tv_recycler_view.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        air_tv_recycler_view.adapter = TvAirAdapter()

        popular_movies_recycler_view.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        popular_movies_recycler_view.adapter = PopularMoviesAdapter()
    }
}