package com.rwawrzyniak.discofetch.business.data.network.implementation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// https://api.discogs.com/database/search response
@Parcelize
data class SearchResponse(
	val pagination: Pagination,
	val searchResults: List<SearchResult>
) : Parcelable

@Parcelize
data class Pagination(
    val items: Int,
    val page: Int,
    val pages: Int,
    val per_page: Int,
    val urls: Urls
) : Parcelable

@Parcelize
data class SearchResult(
	val barcode: List<String>,
	val catno: String,
	val community: Community,
	val country: String,
	val cover_image: String,
	val format: List<String>,
	val genre: List<String>,
	val id: Int,
	val label: List<String>,
	val master_id: Int,
	val master_url: String,
	val resource_url: String,
	val style: List<String>,
	val thumb: String,
	val title: String,
	val type: String,
	val uri: String,
	val user_data: UserData,
	val year: String
) : Parcelable

@Parcelize
data class Urls(
    val last: String,
    val next: String
): Parcelable

@Parcelize
data class Community(
    val have: Int,
    val want: Int
) : Parcelable

@Parcelize
data class UserData(
    val in_collection: Boolean,
    val in_wantlist: Boolean
) : Parcelable
