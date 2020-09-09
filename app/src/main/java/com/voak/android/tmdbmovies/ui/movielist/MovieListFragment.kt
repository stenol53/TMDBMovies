package com.voak.android.tmdbmovies.ui.movielist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.voak.android.tmdbmovies.R
import com.voak.android.tmdbmovies.databinding.FragmentMovieListBinding
import com.voak.android.tmdbmovies.ui.bottomnavigation.home.PopularMoviesAdapter
import com.voak.android.tmdbmovies.ui.bottomnavigation.home.TvAirAdapter
import com.voak.android.tmdbmovies.ui.details.DetailsActivity
import com.voak.android.tmdbmovies.ui.details.movie.MovieDetailsFragment
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class MovieListFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private var binding: FragmentMovieListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)

        binding = FragmentMovieListBinding.bind(view)
        binding?.viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MovieListVieModel::class.java)

        binding?.movieDetailsToolbar?.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        binding?.movieDetailsToolbar?.title =
            when (requireArguments().getInt(KEY_DATA)) {
                ARG_POPULAR_MOVIES_DATA -> getString(R.string.popular_movies)
                ARG_MOVIES_NOW_PLAYING_DATA -> getString(R.string.now_playing)
                ARG_TV_SHOW_DATA -> getString(R.string.tv_shows_on_the_air)
                else -> "123"
            }

        initRecycler()
        bindViews()

        return binding?.root
    }

    private fun initRecycler() {
        binding?.recyclerView?.layoutManager = GridLayoutManager(context, 3)

        when (arguments?.getInt(KEY_DATA)) {
            ARG_POPULAR_MOVIES_DATA, ARG_MOVIES_NOW_PLAYING_DATA -> {
                binding?.recyclerView?.adapter = PopularMoviesAdapter({
                    val intent = DetailsActivity.instance(DetailsActivity.MOVIE_FRAGMENT, it, requireContext())
                    startActivity(intent)
                },
                true
                )
            }
            ARG_TV_SHOW_DATA -> {
                binding?.recyclerView?.adapter = TvAirAdapter({
                    val intent = DetailsActivity.instance(DetailsActivity.TV_SHOW_FRAGMENT, it, requireContext())
                    startActivity(intent)
                },
                true
                )
            }
        }

        binding?.recyclerView?.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastPos: Int = (recyclerView.layoutManager as GridLayoutManager)
                    .findLastVisibleItemPosition()
                val itemsCount: Int =
                    when (requireArguments().getInt(KEY_DATA)) {
                        ARG_POPULAR_MOVIES_DATA, ARG_MOVIES_NOW_PLAYING_DATA -> {
                            (binding?.recyclerView?.adapter as PopularMoviesAdapter).itemCount
                        }
                        ARG_TV_SHOW_DATA -> {
                            (binding?.recyclerView?.adapter as TvAirAdapter).itemCount
                        }
                        else -> {
                            (binding?.recyclerView?.adapter as PopularMoviesAdapter).itemCount
                        }
                    }

                if (dy > 0 && lastPos >= itemsCount - 3) {
                    when (requireArguments().getInt(KEY_DATA)) {
                        ARG_POPULAR_MOVIES_DATA -> {
                            binding?.viewModel?.getPopularMovies()
                        }
                        ARG_MOVIES_NOW_PLAYING_DATA -> {
                            binding?.viewModel?.getMoviesNowPlaying()
                        }
                        ARG_TV_SHOW_DATA -> {
                            binding?.viewModel?.getTvShowsOnTheAir()
                        }
                    }
                }
            }
        })
    }

    private fun bindViews() {
        when (arguments?.getInt(KEY_DATA)) {
            ARG_POPULAR_MOVIES_DATA -> {
                binding?.viewModel?.getPopularMoviesLiveData()?.observe(viewLifecycleOwner) {
                    (binding?.recyclerView?.adapter as? PopularMoviesAdapter)?.setMovies(it)
                }

                binding?.viewModel?.getPopularMovies()
            }
            ARG_MOVIES_NOW_PLAYING_DATA -> {
                binding?.viewModel?.getMoviesNowPlayingLiveData()?.observe(viewLifecycleOwner) {
                    (binding?.recyclerView?.adapter as? PopularMoviesAdapter)?.setMovies(it)
                }

                binding?.viewModel?.getMoviesNowPlaying()
            }
            ARG_TV_SHOW_DATA -> {
                binding?.viewModel?.getTvShowsOnTheAirLiveData()?.observe(viewLifecycleOwner) {
                    (binding?.recyclerView?.adapter as? TvAirAdapter)?.setTvShows(it)
                }

                binding?.viewModel?.getTvShowsOnTheAir()
            }
        }
    }

    companion object {
        private const val KEY_DATA = "key_data"
        const val ARG_POPULAR_MOVIES_DATA = 0
        const val ARG_TV_SHOW_DATA = 1
        const val ARG_MOVIES_NOW_PLAYING_DATA = 2

        fun instance(data: Int): MovieListFragment {
            val arguments = Bundle()
            when (data) {
                ARG_POPULAR_MOVIES_DATA, ARG_TV_SHOW_DATA, ARG_MOVIES_NOW_PLAYING_DATA -> {
                    arguments.putInt(KEY_DATA, data)
                }
            }
            val fragment = MovieListFragment()
            fragment.arguments = arguments

            return fragment
        }
    }

}