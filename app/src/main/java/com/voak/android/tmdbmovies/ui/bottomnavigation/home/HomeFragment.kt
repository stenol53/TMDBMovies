package com.voak.android.tmdbmovies.ui.bottomnavigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.voak.android.tmdbmovies.R
import com.voak.android.tmdbmovies.databinding.FragmentHomeBinding
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

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}