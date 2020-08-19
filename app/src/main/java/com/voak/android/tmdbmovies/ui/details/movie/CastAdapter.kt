package com.voak.android.tmdbmovies.ui.details.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.voak.android.tmdbmovies.R
import com.voak.android.tmdbmovies.model.Cast
import com.voak.android.tmdbmovies.ui.details.movie.CastAdapter.CastViewHolder
import com.voak.android.tmdbmovies.utils.IMAGE_BASE_URL
import java.util.*

class CastAdapter : RecyclerView.Adapter<CastViewHolder>() {
    private var cast: List<Cast> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cast_item, parent, false)

        return CastViewHolder(view)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(cast[position])
    }

    override fun getItemCount(): Int = cast.size

    fun setCast(cast: List<Cast>) {
        this.cast = cast
        notifyDataSetChanged()
    }

    inner class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var cast: Cast
        private val image: ImageView = itemView.findViewById(R.id.cast_image)
        private val name: TextView = itemView.findViewById(R.id.cast_name)
        private val character: TextView = itemView.findViewById(R.id.character_name)

        fun bind(cast: Cast) {
            this.cast = cast

            Picasso.get()
                .load(IMAGE_BASE_URL + this.cast.profile)
                .error(R.drawable.ic_cast_unloaded)
                .into(image)

            name.text = this.cast.name
            character.text = this.cast.character
        }
    }
}