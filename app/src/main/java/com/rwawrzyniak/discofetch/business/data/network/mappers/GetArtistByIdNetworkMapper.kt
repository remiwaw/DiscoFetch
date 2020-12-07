package com.rwawrzyniak.discofetch.business.data.network.mappers

import com.rwawrzyniak.discofetch.business.data.network.implementation.response.GetArtistResponse
import com.rwawrzyniak.discofetch.business.data.network.implementation.response.GetReleaseByIdResponse
import com.rwawrzyniak.discofetch.business.domain.util.EntityMapper
import com.rwawrzyniak.discofetch.business.domain.model.AlbumDetails
import com.rwawrzyniak.discofetch.business.domain.model.Artist
import com.rwawrzyniak.discofetch.business.domain.model.ArtistDetails
import javax.inject.Inject

class GetArtistByIdNetworkMapper @Inject constructor(): EntityMapper<GetArtistResponse, ArtistDetails> {

	override fun mapFromEntity(entity: GetArtistResponse): ArtistDetails {
		return ArtistDetails(
			id = entity.id,
			name = entity.name,
			profile = entity.profile,
			urls = entity.urls
		)
	}

	override fun mapToEntity(domainModel: ArtistDetails): GetArtistResponse {
		TODO("Not yet implemented")
	}
}
