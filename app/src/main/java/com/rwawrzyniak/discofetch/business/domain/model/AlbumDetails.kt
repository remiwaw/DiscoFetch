package com.rwawrzyniak.discofetch.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlbumDetails(
	val id: Long,
	var coverImageUrl: String,
	val title: String,
	val releasedYear: String,
	val artists: List<Artist> = listOf(),
	val tracks: List<String>
) : Parcelable
