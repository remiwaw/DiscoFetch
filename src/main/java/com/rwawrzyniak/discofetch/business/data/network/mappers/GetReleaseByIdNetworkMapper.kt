package com.rwawrzyniak.discofetch.business.data.network.mappers

import com.rwawrzyniak.discofetch.business.data.network.implementation.response.GetReleaseByIdResponse
import com.rwawrzyniak.discofetch.business.domain.util.EntityMapper
import com.rwawrzyniak.discofetch.business.domain.model.AlbumDetails
import com.rwawrzyniak.discofetch.business.domain.model.Artist
import javax.inject.Inject

class GetReleaseByIdNetworkMapper @Inject constructor(): EntityMapper<GetReleaseByIdResponse, AlbumDetails> {

	override fun mapFromEntity(entity: GetReleaseByIdResponse): AlbumDetails {
		return AlbumDetails(
			id = entity.id,
			cover_image_url = entity.thumb,
			title = entity.title,
			releasedYear = entity.released,
			artists = entity.artists.map { Artist(it.id, it.name) },
			tracks = entity.tracklist.map { it.title }
		)
	}

	override fun mapToEntity(domainModel: AlbumDetails): GetReleaseByIdResponse {
		error("Not used yet")
	}
}
