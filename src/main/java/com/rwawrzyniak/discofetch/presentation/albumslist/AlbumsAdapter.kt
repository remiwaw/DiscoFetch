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
import com.rwawrzyniak.discofetch.business.domain.model.AlbumDetails
import com.rwawrzyniak.discofetch.business.domain.model.AlbumInAList
import kotlinx.android.synthetic.main.album_item_layout.view.*

class AlbumsAdapter(private val onClickListener: (AlbumInAList) -> Unit) : PagingDataAdapter<AlbumInAList, AlbumsAdapter.AlbumViewHolder>(DIFF_CALLBACK) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder =
		AlbumViewHolder(
			LayoutInflater.from(parent.context)
				.inflate(R.layout.album_item_layout, parent, false)
		)

	override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
		val albumInAList: AlbumInAList? = getItem(position)

		albumInAList?.let {
			holder.itemView.setOnClickListener{ onClickListener(albumInAList) }
			holder.albumTitle.text = albumInAList.title
			holder.iv.load(albumInAList.cover_image) { placeholder(R.drawable.ic_baseline_block_24) }
		}
	}


	class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val albumTitle = view.album_title_text_view as TextView
		val iv = view.iv as ImageView
	}

	companion object {
		private val DIFF_CALLBACK = object :
			DiffUtil.ItemCallback<AlbumInAList>() {

			override fun areItemsTheSame(
				oldAlbumInAList: AlbumInAList,
				newAlbumInAList: AlbumInAList
			) = oldAlbumInAList.id == newAlbumInAList.id

			override fun areContentsTheSame(
				oldAlbumInAList: AlbumInAList,
				newAlbumInAList: AlbumInAList
			) = oldAlbumInAList == newAlbumInAList
		}
	}
}
