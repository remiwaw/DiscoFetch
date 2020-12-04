package com.rwawrzyniak.discofetch.framework.datasource.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AlbumRemoteKeyEntity(
	@PrimaryKey
	val albumId: Long,
	val prevKey: Int?,
	val nextKey: Int?
)


