package com.rwawrzyniak.discofetch.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Artist(
	val id: Long,
	val name: String
) : Parcelable{
	override fun toString(): String = name
}


