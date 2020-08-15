package com.voak.android.tmdbmovies.ui.bottomnavigation.home

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.voak.android.tmdbmovies.R
import com.voak.android.tmdbmovies.model.TvShow
import com.voak.android.tmdbmovies.ui.bottomnavigation.home.TvAirAdapter.TvAirViewHolder
import com.voak.android.tmdbmovies.utils.IMAGE_BASE_URL
import java.util.*

class TvAirAdapter(private val holderOnClick: (Int) -> Unit) : RecyclerView.Adapter<TvAirViewHolder>() {
    private var tvShows: List<TvShow> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvAirViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tv_air_item, parent, false)

        return TvAirViewHolder(view)
    }

    override fun getItemCount(): Int = tvShows.size

    override fun onBindViewHolder(holder: TvAirViewHolder, position: Int) {
        holder.bind(tvShows[position])
    }

    fun setTvShows(shows: List<TvShow>) {
        tvShows = shows
        notifyDataSetChanged()
    }

    inner class TvAirViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var tvShow: TvShow
        private val poster: ImageView = itemView.findViewById(R.id.tv_air_poster)
        private val title: TextView = itemView.findViewById(R.id.tv_air_title)
        private val rating: TextView = itemView.findViewById(R.id.tv_air_rating_view)

        init {
            itemView.setOnClickListener {
                holderOnClick(tvShow.id)
            }
        }

        fun bind(tvShow: TvShow) {
            this.tvShow = tvShow

            Picasso.get()
                .load(IMAGE_BASE_URL + this.tvShow.posterPath)
                .into(poster)

            title.text = this.tvShow.title

            title.text = this.tvShow.title
            rating.text = String.format("%.1f", this.tvShow.voteAverage).also {
                val background: Drawable? = when {
                    it.toDouble() >= 7.0 -> {
                        ResourcesCompat.getDrawable(itemView.resources, R.drawable.oval_green, null)
                    }
                    it.toDouble() >= 5.0 && it.toDouble() < 7.0 -> {
                        ResourcesCompat.getDrawable(itemView.resources, R.drawable.oval_yellow, null)
                    }
                    it.toDouble() < 5.0 -> {
                        ResourcesCompat.getDrawable(itemView.resources, R.drawable.oval_red, null)
                    }
                    else -> {
                        null
                    }
                }
                rating.background = background
            }
        }
    }
}