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
import com.voak.android.tmdbmovies.ui.bottomnavigation.home.PopularMoviesAdapter.PopularMoviesViewHolder
import com.voak.android.tmdbmovies.utils.IMAGE_BASE_URL
import java.util.*

class PopularMoviesAdapter(private val holderOnClick: (Int) -> Unit) : RecyclerView.Adapter<PopularMoviesViewHolder>() {
    private var movies: List<Movie> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.popular_movie_item, parent, false)

        return PopularMoviesViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun setMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    inner class PopularMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var movie: Movie
        private val poster: ImageView = itemView.findViewById(R.id.popular_movie_poster)
        private val title: TextView = itemView.findViewById(R.id.popular_movie_title)
        private val rating: TextView = itemView.findViewById(R.id.popular_movie_rating_view)

        init {
            itemView.setOnClickListener {
                holderOnClick(movie.id)
            }
        }

        fun bind(movie: Movie) {
            this.movie = movie

            Picasso.get()
                .load(IMAGE_BASE_URL + movie.posterPath)
                .into(poster)

            title.text = this.movie.title

            rating.text = String.format("%.1f", this.movie.voteAverage).replace(",",".")
            when {
                this.movie.voteAverage >= 7.0 -> {
                    rating.setBackgroundResource(R.drawable.oval_green)
                }

                this.movie.voteAverage >= 5.0 && this.movie.voteAverage < 7.0 -> {
                    rating.setBackgroundResource(R.drawable.oval_yellow)
                }
                this.movie.voteAverage < 5.0 -> {
                    rating.setBackgroundResource(R.drawable.oval_yellow)
                }
            }
        }
    }
}