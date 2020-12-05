package com.rwawrzyniak.discofetch.presentation.albumslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.rwawrzyniak.discofetch.R
import com.rwawrzyniak.discofetch.business.domain.model.Album
import kotlinx.android.synthetic.main.album_item_layout.view.*

class AlbumsAdapter : PagingDataAdapter<Album, AlbumsAdapter.AlbumViewHolder>(DIFF_CALLBACK) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder =
		AlbumViewHolder(
			LayoutInflater.from(parent.context)
				.inflate(R.layout.album_item_layout, parent, false)
		)

	override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
		val album: Album? = getItem(position)

		album?.let {

			holder.albumTitle.text = album.title
			holder.iv.load(album.cover_image) { placeholder(R.drawable.ic_baseline_block_24) }

			holder.iv.setOnClickListener {
				// TODO navigate to details
			}
		}
	}


	class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val albumTitle = view.album_title_text_view as TextView
		val iv = view.iv as ImageView
	}

	companion object {
		private val DIFF_CALLBACK = object :
			DiffUtil.ItemCallback<Album>() {

			override fun areItemsTheSame(
				oldAlbum: Album,
				newAlbum: Album
			) = oldAlbum.id == newAlbum.id

			override fun areContentsTheSame(
				oldAlbum: Album,
				newAlbum: Album
			) = oldAlbum == newAlbum
		}
	}
}
