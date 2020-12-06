package com.rwawrzyniak.discofetch.business.data.network.implementation.response

data class GetReleaseByIdResponse(
	val artists: List<Artist>,
	val community: Community,
	val companies: List<Company>,
	val country: String,
	val data_quality: String,
	val date_added: String,
	val date_changed: String,
	val estimated_weight: Int,
	val extraartists: List<Extraartist>,
	val format_quantity: Int,
	val formats: List<Format>,
	val genres: List<String>,
	val id: Long,
	val identifiers: List<Any>,
	val images: List<Image>,
	val labels: List<Label>,
	val lowest_price: Double,
	val master_id: Int,
	val master_url: String,
	val notes: String,
	val num_for_sale: Int,
	val released: String,
	val released_formatted: String,
	val resource_url: String,
	val series: List<Any>,
	val status: String,
	val styles: List<String>,
	val thumb: String,
	val title: String,
	val tracklist: List<Tracklist>,
	val uri: String,
	val videos: List<Any>,
	val year: Int
)

data class Artist(
    val anv: String,
    val id: Long,
    val join: String,
    val name: String,
    val resource_url: String,
    val role: String,
    val tracks: String
)

data class Community(
	val contributors: List<Contributor>,
	val data_quality: String,
	val have: Int,
	val rating: Rating,
	val status: String,
	val submitter: Submitter,
	val want: Int
)

data class Company(
    val catno: String,
    val entity_type: String,
    val entity_type_name: String,
	val id: Long,
    val name: String,
    val resource_url: String
)

data class Extraartist(
    val anv: String,
	val id: Long,
    val join: String,
    val name: String,
    val resource_url: String,
    val role: String,
    val tracks: String
)

data class Format(
    val descriptions: List<String>,
    val name: String,
    val qty: String
)

data class Image(
    val height: Int,
    val resource_url: String,
    val type: String,
    val uri: String,
    val uri150: String,
    val width: Int
)

data class Label(
    val catno: String,
    val entity_type: String,
	val id: Long,
    val name: String,
    val resource_url: String
)

data class Tracklist(
    val duration: String,
    val position: String,
    val title: String,
    val type_: String
)

data class Contributor(
    val resource_url: String,
    val username: String
)

data class Rating(
    val average: Double,
    val count: Int
)

data class Submitter(
    val resource_url: String,
    val username: String
)
