package com.voak.android.tmdbmovies.ui.details.tv

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.voak.android.tmdbmovies.R
import com.voak.android.tmdbmovies.databinding.FragmentTvShowDetailsBinding
import com.voak.android.tmdbmovies.ui.bottomnavigation.home.TvAirAdapter
import com.voak.android.tmdbmovies.ui.details.movie.CastAdapter
import com.voak.android.tmdbmovies.ui.details.movie.VideosAdapter
import com.voak.android.tmdbmovies.ui.person.PersonDetailsActivity
import com.voak.android.tmdbmovies.utils.IMAGE_BASE_URL
import com.voak.android.tmdbmovies.utils.WIDE_IMAGE_BASE_URL
import com.voak.android.tmdbmovies.utils.YOUTUBE_VIDEO_URL
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class TvShowDetailsFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private var binding: FragmentTvShowDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tv_show_details, container, false)

        binding = FragmentTvShowDetailsBinding.bind(view)
        binding?.viewModel = ViewModelProvider(this, viewModelFactory)
            .get(TvShowDetailsViewModel::class.java)

        initRecyclers()
        bindViews()

        return binding?.root
    }

    private fun initRecyclers() {
        binding?.videoRecyclerView?.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        binding?.videoRecyclerView?.adapter = VideosAdapter() {
            openVideo(it)
        }

        binding?.castRecyclerView?.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        binding?.castRecyclerView?.adapter = CastAdapter() {
            startActivity(PersonDetailsActivity.newIntent(it, requireContext()))
        }

        binding?.similarRecyclerView?.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }

        binding?.similarRecyclerView?.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastPos: Int = (recyclerView.layoutManager as LinearLayoutManager)
                    .findLastVisibleItemPosition()
                val itemsCount: Int = (binding?.similarRecyclerView?.adapter as TvAirAdapter).itemCount

                if (dx > 0 && lastPos >= itemsCount - 3) {
                    binding?.viewModel?.loadSimilar(requireArguments().getInt(ARG_TV_SHOW_ID))
                }
            }
        })

        binding?.similarRecyclerView?.adapter = TvAirAdapter() {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, instance(it))
                .addToBackStack(null)
                .commit()
        }
    }

    private fun bindViews() {
        binding?.tvDetailsToolbar?.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        binding?.tvDetailsToolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.favourite -> {
                    true
                }
                else -> false
            }
        }

        binding?.viewModel?.getLoadingLiveData()?.observe(viewLifecycleOwner) {
            if (it) {
                binding?.tvDetailsContent?.visibility = View.GONE
                binding?.appBarLayout?.visibility = View.GONE
                binding?.tvDetailsProgress?.visibility = View.VISIBLE
            } else {
                binding?.tvDetailsProgress?.visibility = View.GONE
                binding?.tvDetailsContent?.visibility = View.VISIBLE
                binding?.appBarLayout?.visibility = View.VISIBLE
            }
        }

        binding?.viewModel?.getTvShowLiveData()?.observe(viewLifecycleOwner) { details ->
            if (details != null) {

                binding?.tvDetailsToolbar?.title = details.name

                Picasso.get()
                    .load(WIDE_IMAGE_BASE_URL + details.widePoster)
                    .into(binding?.tvDetailsWidePoster)

                Picasso.get()
                    .load(IMAGE_BASE_URL + details.poster)
                    .into(binding?.tvDetailsPoster)

                binding?.tvReleaseTextView?.text = getString(R.string.release_date, details.release)
                binding?.tvRuntimeTextView?.text = getString(R.string.runtime, details.runtime[0])
                binding?.tvRatingTextView?.text = getString(R.string.rating, details.rating.toString())
                binding?.tvOverviewTextView?.text = details.overview

                val genres = let {
                    var str = ""
                    details.genres.forEach { genre -> str += "${genre.name}, " }
                    str.dropLast(2)
                }
                binding?.tvGenresTextView?.text = getString(R.string.genres, genres)
                binding?.seasonsCountTextView?.text = getString(R.string.seasons, details.seasonsCount)
                binding?.episodesCountTextView?.text = getString(R.string.episodes, details.episodesCount)
            }
        }

        binding?.viewModel?.getVideosLiveData()?.observe(viewLifecycleOwner) {
            (binding?.videoRecyclerView?.adapter as VideosAdapter).setVideos(it)
        }

        binding?.viewModel?.getCastLiveData()?.observe(viewLifecycleOwner) {
            (binding?.castRecyclerView?.adapter as CastAdapter).setCast(it)
        }

        binding?.viewModel?.getSimilarTvShowsLiveData()?.observe(viewLifecycleOwner) {
            (binding?.similarRecyclerView?.adapter as TvAirAdapter).setTvShows(it)
        }

        binding?.viewModel?.onAttached(requireArguments().getInt(ARG_TV_SHOW_ID))
    }

    private fun openVideo(key: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_VIDEO_URL + key))
        try {
            startActivity(intent)
        } catch (ex : ActivityNotFoundException) {
            Log.i("MovieDetailsFragment", ex.message.orEmpty())
        }
    }

    companion object {
        private const val ARG_TV_SHOW_ID = "tv_show_id"

        fun instance(movieId: Int): TvShowDetailsFragment {
            val args = Bundle()
            args.putInt(ARG_TV_SHOW_ID, movieId)
            return TvShowDetailsFragment().apply {
                arguments = args
            }
        }
    }
}