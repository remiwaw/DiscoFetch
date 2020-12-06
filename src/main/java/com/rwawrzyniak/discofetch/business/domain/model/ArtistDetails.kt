package com.rwawrzyniak.discofetch.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArtistDetails(
	val id: Long,
	val name: String,
	val profile: String,
	val urls: List<String>
) : Parcelable
