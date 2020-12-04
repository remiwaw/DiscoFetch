package com.rwawrzyniak.discofetch.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Album(
	val id: Int,
	val cover_image: String,
	val title: String
) : Parcelable
