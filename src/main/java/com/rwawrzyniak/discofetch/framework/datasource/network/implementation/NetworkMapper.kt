package com.rwawrzyniak.discofetch.framework.datasource.network.implementation

import com.rwawrzyniak.discofetch.business.data.network.implementation.SearchResult
import com.rwawrzyniak.discofetch.business.domain.util.EntityMapper
import com.rwawrzyniak.discofetch.framework.datasource.cache.model.AlbumCacheEntity
import javax.inject.Inject

class NetworkMapper @Inject constructor(): EntityMapper<SearchResult, AlbumCacheEntity> {

	override fun mapToEntity(domainModel: AlbumCacheEntity): SearchResult {
		error("Not used yet")
	}

	override fun mapFromEntity(entity: SearchResult) = AlbumCacheEntity(
		id = entity.id,
		cover_image = entity.cover_image,
		title = entity.title
	)
}







