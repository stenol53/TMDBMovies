package com.voak.android.tmdbmovies.ui.bottomnavigation.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.voak.android.tmdbmovies.R
import dagger.android.support.DaggerFragment

class FavouriteFragment : DaggerFragment() {

    private lateinit var favouriteViewModel: FavouriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favouriteViewModel =
            ViewModelProviders.of(this).get(FavouriteViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_favourite, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        favouriteViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}