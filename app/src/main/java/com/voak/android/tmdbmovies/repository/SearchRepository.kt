package com.voak.android.tmdbmovies.repository

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputEditText
import com.voak.android.tmdbmovies.api.SearchService
import com.voak.android.tmdbmovies.model.*
import com.voak.android.tmdbmovies.utils.SharedPreferences
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchRepository @Inject constructor(private val searchService: SearchService, private val context: Context) {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies
    private val _tvShows = MutableLiveData<List<TvShow>>()
    val tvShows: LiveData<List<TvShow>> = _tvShows
    private val _persons = MutableLiveData<List<Cast>>()
    val persons: LiveData<List<Cast>> = _persons
    private val _moviesTotalResults = MutableLiveData<Int>()
    val moviesTotalResults: LiveData<Int> = _moviesTotalResults
    private val _tvTotalResults = MutableLiveData<Int>()
    val tvTotalResults: LiveData<Int> = _tvTotalResults
    private val _personTotalResults = MutableLiveData<Int>()
    val personTotalResults: LiveData<Int> = _personTotalResults
    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> = _progress

    private var moviesCurrentPage: Int = 1
    private var moviesTotalPage: Int = 0
    private var tvCurrentPage: Int = 1
    private var tvTotalPage: Int = 0
    private var personCurrentPage: Int = 1
    private var personTotalPage: Int = 0
    private var isFetching = false

    @SuppressLint("CheckResult")
    fun searchMovies(searchView: TextInputEditText) {
        Observable.create(ObservableOnSubscribe<String> { subscriber ->
            searchView.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    subscriber.onNext(p0.toString())
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
        }).map { text -> text.trim() }
            .debounce(250, TimeUnit.MILLISECONDS)
            .subscribe { text ->
                SharedPreferences.setSearchQuery(context, text)
                _progress.postValue(true)
                Observable.zip(
                    searchService.searchMovies(text),
                    searchService.searchTv(text),
                    searchService.searchPerson(text),
                    Function3<MovieResult, TvResult, SearchPersonResult, Map<String, Any>>() { movieRes, tvRes, personRes ->
                            mapOf(
                                MOVIES to movieRes,
                                TV_SHOWS to tvRes,
                                PERSON to personRes
                            )
                    }
                ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result ->
                            val movies = result[MOVIES] as? MovieResult
                            val tv = result[TV_SHOWS] as? TvResult
                            val persons = result[PERSON] as? SearchPersonResult

                            movies?.totalPages?.let {
                                moviesTotalPage = it
                            }

                            tv?.pages?.let {
                                tvTotalPage = it
                            }

                            persons?.totalPages?.let {
                                personTotalPage = it
                            }

                            _personTotalResults.value = persons?.totalResults
                            _persons.value = persons?.result
                            _moviesTotalResults.value = movies?.totalResults
                            _tvTotalResults.value = tv?.totalResults
                            _movies.value = movies?.result
                            _tvShows.value = tv?.result
                            _progress.value = false
                        },
                        { error ->
                            Log.i(TAG, error.localizedMessage.orEmpty())
                            Log.i(TAG, "error")
                            _progress.value = false
                        }
                    )
            }
    }

    fun updateMovies() {
        if (moviesCurrentPage + 1 <= moviesTotalPage && !isFetching) {
            isFetching = true
            moviesCurrentPage++
            SharedPreferences.getSearchQuery(context)?.let {
                searchService.searchMovies(it, moviesCurrentPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result ->
                            val list = _movies.value?.toMutableList()
                            list?.addAll(result.result)
                            _movies.value = list?.toList()
                            isFetching = false
                        },
                        { error ->
                            Log.i(TAG, error.localizedMessage.orEmpty())
                            isFetching = false
                        }
                    )
            }
        }
    }

    fun updateTv() {
        if (tvCurrentPage + 1 <= tvTotalPage && !isFetching) {
            isFetching = true
            tvCurrentPage++
            SharedPreferences.getSearchQuery(context)?.let {
                searchService.searchTv(it, tvCurrentPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result ->
                            val list = _tvShows.value?.toMutableList()
                            list?.addAll(result.result)
                            _tvShows.value = list?.toList()
                            isFetching = false
                        },
                        { error ->
                            Log.i(TAG, error.localizedMessage.orEmpty())
                            isFetching = false
                        }
                    )
            }
        }
    }

    fun updatePersons() {
        if (personCurrentPage + 1 <= personTotalPage && !isFetching) {
            isFetching = true
            personCurrentPage++
            SharedPreferences.getSearchQuery(context)?.let {
                searchService.searchPerson(it, personCurrentPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result ->
                            val list = _persons.value?.toMutableList()
                            list?.addAll(result.result)
                            _persons.value = list?.toList()
                            isFetching = false
                        },
                        { error ->
                            Log.i(TAG, error.localizedMessage.orEmpty())
                            isFetching = false
                        }
                    )
            }
        }
    }

    companion object {
        private const val MOVIES = "movies"
        private const val TV_SHOWS = "tv_shows"
        private const val PERSON = "person"
        private const val TAG = "SearchRepository"
    }
}