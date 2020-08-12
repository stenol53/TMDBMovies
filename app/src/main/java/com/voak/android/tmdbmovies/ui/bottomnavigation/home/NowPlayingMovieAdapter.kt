package com.voak.android.tmdbmovies.ui.bottomnavigation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.voak.android.tmdbmovies.R
import com.voak.android.tmdbmovies.model.Movie
import com.voak.android.tmdbmovies.ui.bottomnavigation.home.NowPlayingMovieAdapter.NowPlayingMovieViewHolder
import com.voak.android.tmdbmovies.utils.WIDE_IMAGE_BASE_URL
import java.util.*

class NowPlayingMovieAdapter : RecyclerView.Adapter<NowPlayingMovieViewHolder>() {

    private var movies: List<Movie> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingMovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.now_playing_movie_item, parent, false)

        return NowPlayingMovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: NowPlayingMovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun setMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    inner class NowPlayingMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.findViewById(R.id.now_playing_poster)
        private val title: TextView = itemView.findViewById(R.id.now_playing_title)
        private lateinit var movie: Movie

        fun bind(movie: Movie) {
            this.movie = movie

            Picasso.get()
                .load(WIDE_IMAGE_BASE_URL + this.movie.widePosterPath)
                .into(poster)

            title.text = this.movie.title
        }
    }
}