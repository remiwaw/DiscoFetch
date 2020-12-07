package com.rwawrzyniak.discofetch.presentation.recorddetail.state

import android.text.Spannable
import android.text.SpannableString
import com.rwawrzyniak.discofetch.business.domain.model.Artist

data class AlbumDetailViewState(
	var cover_image_url: String = "",
	val title: String,
	val releasedYear: SpannableString,
	val artists: List<Artist>,
	val tracks: Spannable
)
