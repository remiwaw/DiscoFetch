package com.rwawrzyniak.discofetch.presentation.recorddetail.state

import android.text.Spannable
import android.text.SpannableString

data class AlbumDetailViewState(
	var cover_image_url: String = "",
	val title: String,
	val releasedYear: SpannableString,
	val artists: Spannable,
	val tracks: Spannable
)
