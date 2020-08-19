package com.voak.android.tmdbmovies.ui.details.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import com.voak.android.tmdbmovies.R
import com.voak.android.tmdbmovies.model.Video
import com.voak.android.tmdbmovies.ui.details.movie.VideosAdapter.VideosViewHolder
import com.voak.android.tmdbmovies.utils.YOUTUBE_VIDEO_IMAGE_URL
import java.util.*

class VideosAdapter(private val holderCallback: (String) -> Unit) : RecyclerView.Adapter<VideosViewHolder>() {
    private var videos: List<Video> = Collections.emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.video_item, parent, false)

        return VideosViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    override fun getItemCount(): Int = videos.size

    fun setVideos(videos: List<Video>) {
        this.videos = videos
        notifyDataSetChanged()
    }

    inner class VideosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var video: Video
        private val image: ImageView = itemView.findViewById(R.id.video_image)
        private val name: TextView = itemView.findViewById(R.id.video_name)

        init {
            itemView.setOnClickListener {
                holderCallback(this.video.key)
            }
        }

        fun bind(video: Video) {
            this.video = video

            name.text = this.video.name
            Picasso.get()
                .load(YOUTUBE_VIDEO_IMAGE_URL + this.video.key + "/mqdefault.jpg")
                .into(image)
        }
    }
}