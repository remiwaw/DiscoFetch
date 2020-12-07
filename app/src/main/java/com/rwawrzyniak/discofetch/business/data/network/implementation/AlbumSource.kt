package com.rwawrzyniak.discofetch.business.data.network.implementation

import com.rwawrzyniak.discofetch.business.data.network.abstraction.DiscogsNetworkDataSource
import com.rwawrzyniak.discofetch.business.data.network.implementation.response.GetReleaseByIdResponse
import com.rwawrzyniak.discofetch.business.data.network.mappers.SearchResponseNetworkMapper
import javax.inject.Inject

class AlbumSource @Inject constructor(private val api: DiscogsNetworkDataSource,
									  private val searchResponseNetworkMapper: SearchResponseNetworkMapper,
) {

	suspend fun getAlbumById(albumId: Long){
		val response: GetReleaseByIdResponse = api.getReleaseById(albumId)
	}
}
