package com.rwawrzyniak.discofetch.presentation.recorddetail

import android.annotation.SuppressLint
import android.content.Context
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.core.text.toSpannable
import androidx.databinding.BindingAdapter
import com.rwawrzyniak.discofetch.R
import com.rwawrzyniak.discofetch.business.domain.model.Artist

@BindingAdapter("app:items")
fun setItems(listView: ListView, items: List<Artist>?) {
	items.let {
		(listView.adapter as ArtistListAdapter).submitList(items)
	}
}

class ArtistListAdapter(context: Context, var artists: List<Artist> = arrayListOf()) :
    ArrayAdapter<Artist>(context, 0, artists) {

	@SuppressLint("ViewHolder") // Its only static list, we want be adding many or new elements
	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
		val listItem = LayoutInflater.from(context).inflate(
			R.layout.artist_item_layout,
			parent,
			false
		)

		val artists : TextView = listItem.findViewById(R.id.artistName)
		val artistName = this.artists[position].name

		val artistNameUnderlined = SpannableString(artistName)
		artistNameUnderlined.setSpan(UnderlineSpan(),0,artistName.length,0)

		artists.text = artistNameUnderlined.toSpannable()

		return listItem
    }

    override fun getCount(): Int {
        return artists.size
    }

	override fun getItem(position: Int): Artist {
		return artists[position]
	}

    fun submitList(items: List<Artist>?) {
		if (items != null) {
			this.artists = items
			notifyDataSetChanged()
		}
    }
}
