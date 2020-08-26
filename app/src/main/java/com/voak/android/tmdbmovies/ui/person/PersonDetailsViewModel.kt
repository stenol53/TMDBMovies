package com.voak.android.tmdbmovies.ui.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.voak.android.tmdbmovies.model.Movie
import com.voak.android.tmdbmovies.model.PersonDetails
import com.voak.android.tmdbmovies.model.TvShow
import com.voak.android.tmdbmovies.repository.PersonDetailsRepository
import javax.inject.Inject

class PersonDetailsViewModel @Inject constructor(private val personDetailsRepository: PersonDetailsRepository) : ViewModel() {

    fun getAllData(id: Int) {
        personDetailsRepository.getAllData(id)
    }

    fun getPersonDetailsLiveData(): LiveData<PersonDetails> {
        return personDetailsRepository.personLiveData
    }

    fun getLoadingLiveData(): LiveData<Boolean> {
        return personDetailsRepository.loading
    }

    fun getPersonMoviesLiveData(): LiveData<List<Movie>> {
        return personDetailsRepository.personMovies
    }

    fun getPersonTvShowsLiveData(): LiveData<List<TvShow>> {
        return personDetailsRepository.personTvShows
    }
}