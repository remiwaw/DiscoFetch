package com.rwawrzyniak.discofetch.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// This is a model used for displaying in a recycler list, so not much details here
@Parcelize
data class AlbumInAList(
	val id: Long,
	val cover_image: String,
	val title: String
) : Parcelable
