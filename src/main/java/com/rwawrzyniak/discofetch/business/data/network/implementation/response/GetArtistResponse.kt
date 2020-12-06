package com.rwawrzyniak.discofetch.business.data.network.implementation.response

data class GetArtistResponse(
    val data_quality: String,
    val id: Long,
    val images: List<Image>,
    val members: List<Member>,
    val name: String,
    val namevariations: List<String>,
    val profile: String,
    val releases_url: String,
    val resource_url: String,
    val uri: String,
    val urls: List<String>
)

data class Member(
    val active: Boolean,
    val id: Long,
    val name: String,
    val resource_url: String
)
