package com.voak.android.tmdbmovies.ui.person

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.voak.android.tmdbmovies.R
import com.voak.android.tmdbmovies.databinding.FragmentPersonDetailsBinding
import com.voak.android.tmdbmovies.ui.bottomnavigation.home.PopularMoviesAdapter
import com.voak.android.tmdbmovies.ui.bottomnavigation.home.TvAirAdapter
import com.voak.android.tmdbmovies.ui.details.DetailsActivity
import com.voak.android.tmdbmovies.utils.IMAGE_BASE_URL
import dagger.android.support.DaggerFragment
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.abs

class PersonDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var binding: FragmentPersonDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_person_details, container, false)

        binding = FragmentPersonDetailsBinding.bind(view)
        binding?.viewModel = ViewModelProvider(this, viewModelFactory)
            .get(PersonDetailsViewModel::class.java)

        initRecyclers()
        bindViews()

        return binding?.root
    }

    private fun bindViews() {
        binding?.personToolbar?.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        binding?.viewModel?.getLoadingLiveData()?.observe(viewLifecycleOwner) {
            if (it) {
                binding?.personContent?.visibility = View.GONE
                binding?.appBarLayout?.visibility = View.GONE
                binding?.personProgress?.visibility = View.VISIBLE
            } else {
                binding?.personProgress?.visibility = View.GONE
                binding?.personContent?.visibility = View.VISIBLE
                binding?.appBarLayout?.visibility = View.VISIBLE
            }
        }

        binding?.viewModel?.getPersonDetailsLiveData()?.observe(viewLifecycleOwner) {
            Picasso.get()
                .load(IMAGE_BASE_URL + it.poster)
                .into(binding?.personWidePoster)

            Picasso.get()
                .load(IMAGE_BASE_URL + it.poster)
                .into(binding?.personPoster)

            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val birth = sdf.parse(it.birthday)
            val now = Date()
            val diffInMillies: Long = abs(now.time - birth.time)
            val age = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) / 365

            binding?.personToolbar?.title = it.name
            binding?.biographyTextView?.text = it.biography
            binding?.ageTextView?.text = getString(R.string.age, age)
            binding?.birthdayTextView?.text = getString(R.string.birthday, it.birthday)
            binding?.placeOfBirthTextView?.text = getString(
                R.string.place_of_birth,
                it.placeOfBirth
            )
        }

        binding?.viewModel?.getPersonMoviesLiveData()?.observe(viewLifecycleOwner) {
            (binding?.moviesRecyclerView?.adapter as? PopularMoviesAdapter)?.setMovies(it)
        }

        binding?.viewModel?.getPersonTvShowsLiveData()?.observe(viewLifecycleOwner) {
            (binding?.tvShowsRecyclerView?.adapter as? TvAirAdapter)?.setTvShows(it)
        }

        binding?.viewModel?.getAllData(requireArguments().getInt(ARG_PERSON_ID))

    }

    private fun initRecyclers() {
        binding?.moviesRecyclerView?.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        binding?.moviesRecyclerView?.adapter = PopularMoviesAdapter({
            navigateToTvShowDetailsCallback(it)
        })

        binding?.tvShowsRecyclerView?.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        binding?.tvShowsRecyclerView?.adapter = TvAirAdapter({
            navigateToMovieDetailsCallback(it)
        })
    }

    private fun navigateToMovieDetailsCallback(id: Int) {
        val intent = DetailsActivity.instance(DetailsActivity.MOVIE_FRAGMENT, id, requireContext())
        startActivity(intent)
    }

    private fun navigateToTvShowDetailsCallback(id: Int) {
        val intent = DetailsActivity.instance(
            DetailsActivity.TV_SHOW_FRAGMENT,
            id,
            requireContext()
        )
        startActivity(intent)
    }

    companion object {
        private const val ARG_PERSON_ID = "person_id"

        fun instance(id: Int): PersonDetailsFragment {
            val arguments = Bundle()
            arguments.putInt(ARG_PERSON_ID, id)

            val fragment = PersonDetailsFragment()
            fragment.arguments = arguments

            return fragment
        }
    }

}