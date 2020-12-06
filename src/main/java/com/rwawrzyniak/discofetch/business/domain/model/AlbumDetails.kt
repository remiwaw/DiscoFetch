package com.rwawrzyniak.discofetch.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlbumDetails(
	val id: Long,
	var cover_image_url: String = "",
	val title: String,
	val releasedYear: String,
	val artists: List<Artist>,
	val tracks: List<String>
) : Parcelable
