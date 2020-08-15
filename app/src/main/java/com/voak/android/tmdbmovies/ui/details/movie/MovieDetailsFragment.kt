package com.voak.android.tmdbmovies.ui.details.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.squareup.picasso.Picasso
import com.voak.android.tmdbmovies.R
import com.voak.android.tmdbmovies.databinding.FragmentMovieDetailsBinding
import com.voak.android.tmdbmovies.utils.IMAGE_BASE_URL
import com.voak.android.tmdbmovies.utils.WIDE_IMAGE_BASE_URL
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_movie_details.*
import javax.inject.Inject

class MovieDetailsFragment : DaggerFragment() {

    companion object {
        private const val ARG_MOVIE_ID = "movie_id"

        fun instance(movieId: Int): MovieDetailsFragment {
            val args = Bundle()
            args.putInt(ARG_MOVIE_ID, movieId)
            return MovieDetailsFragment().apply {
                arguments = args
            }
        }

    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var binding: FragmentMovieDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_details, container, false)

        val movieDetailsViewModel = ViewModelProvider(this, viewModelFactory)
            .get(MovieDetailsViewModel::class.java)

        binding = FragmentMovieDetailsBinding.bind(view).apply {
            viewModel = movieDetailsViewModel
            lifecycleOwner = this@MovieDetailsFragment
        }

        bindViews()

        return binding?.root
    }

    private fun bindViews() {
        binding?.movieDetailsToolbar?.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        binding?.movieDetailsToolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.favourite -> {
                    true
                }
                else -> false
            }
        }

        binding?.viewModel?.loading?.observe(viewLifecycleOwner) {
            if (it) {
                binding?.movieDetailsContent?.visibility = View.GONE
                binding?.appBarLayout?.visibility = View.GONE
                binding?.movieDetailsProgress?.visibility = View.VISIBLE
            } else {
                binding?.movieDetailsProgress?.visibility = View.GONE
                binding?.movieDetailsContent?.visibility = View.VISIBLE
                binding?.appBarLayout?.visibility = View.VISIBLE
            }
        }

        binding?.viewModel?.movie?.observe(viewLifecycleOwner) { details ->
            if (details != null) {

                binding?.movieDetailsToolbar?.title = details.title

                Picasso.get()
                    .load(WIDE_IMAGE_BASE_URL + details.backdropPath)
                    .into(binding?.movieDetailsWidePoster)

                Picasso.get()
                    .load(IMAGE_BASE_URL + details.posterPath)
                    .into(binding?.movieDetailsPoster)

                binding?.movieReleaseTextView?.text = getString(R.string.release_date, details.releaseDate)
                binding?.movieRuntimeTextView?.text = getString(R.string.runtime, details.runtime)
                binding?.movieRatingTextView?.text = getString(R.string.rating, details.voteAverage.toString())
                binding?.movieOverviewTextView?.text = details.overview

                val genres = let {
                    var str = ""
                    details.genres.forEach { genre -> str += "${genre.name}, " }
                    str.dropLast(2)
                }
                binding?.movieGenresTextView?.text = getString(R.string.genres, genres)
                binding?.movieBudgetTextView?.text = getString(R.string.budget, details.budget.toString())
                binding?.movieRevenueTextView?.text = getString(R.string.revenue, details.revenue.toString())
            }
        }

        binding?.viewModel?.onAttached(requireArguments().getInt(ARG_MOVIE_ID))
    }

}