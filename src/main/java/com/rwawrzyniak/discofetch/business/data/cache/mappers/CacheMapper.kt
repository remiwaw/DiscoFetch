package com.rwawrzyniak.discofetch.business.data.cache.mappers

import com.rwawrzyniak.discofetch.business.domain.model.Album
import com.rwawrzyniak.discofetch.business.domain.util.EntityMapper
import com.rwawrzyniak.discofetch.business.domain.model.AlbumCacheEntity
import javax.inject.Inject

class CacheMapper @Inject constructor(): EntityMapper<AlbumCacheEntity, Album> {

    override fun mapFromEntity(entity: AlbumCacheEntity): Album {
        return Album(
            id = entity.id,
            title = entity.title,
            cover_image = entity.cover_image
        )
    }

    override fun mapToEntity(domainModel: Album): AlbumCacheEntity {
        return AlbumCacheEntity(
            id = domainModel.id,
            title = domainModel.title,
			cover_image = domainModel.cover_image
        )
    }
}







