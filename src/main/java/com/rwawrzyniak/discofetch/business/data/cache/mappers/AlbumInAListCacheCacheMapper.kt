package com.rwawrzyniak.discofetch.business.data.cache.mappers

import com.rwawrzyniak.discofetch.business.domain.model.AlbumCacheEntity
import com.rwawrzyniak.discofetch.business.domain.model.AlbumInAList
import com.rwawrzyniak.discofetch.business.domain.util.EntityMapper
import javax.inject.Inject

class AlbumInAListCacheCacheMapper @Inject constructor(): EntityMapper<AlbumCacheEntity, AlbumInAList> {

    override fun mapFromEntity(entity: AlbumCacheEntity): AlbumInAList {
        return AlbumInAList(
            id = entity.id,
            title = entity.title,
            cover_image = entity.cover_image
        )
    }

    override fun mapToEntity(domainModel: AlbumInAList): AlbumCacheEntity {
        return AlbumCacheEntity(
            id = domainModel.id,
            title = domainModel.title,
			cover_image = domainModel.cover_image
        )
    }
}







