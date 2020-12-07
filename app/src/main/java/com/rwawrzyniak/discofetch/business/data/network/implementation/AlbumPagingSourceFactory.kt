package com.rwawrzyniak.discofetch.business.data.network.implementation

import androidx.paging.ExperimentalPagingApi
import com.rwawrzyniak.discofetch.business.data.cache.abstraction.CacheDb
import com.rwawrzyniak.discofetch.business.data.cache.mappers.AlbumInAListCacheCacheMapper
import com.rwawrzyniak.discofetch.business.data.network.abstraction.DiscogsNetworkDataSource
import com.rwawrzyniak.discofetch.business.data.network.mappers.SearchResponseNetworkMapper
import javax.inject.Inject

@ExperimentalPagingApi
class AlbumPagingSourceFactory @ExperimentalPagingApi
@Inject constructor(private val api: DiscogsNetworkDataSource,
					private val searchResponseNetworkMapper: SearchResponseNetworkMapper,
					private val albumInAListCacheCacheMapper: AlbumInAListCacheCacheMapper,
					private val db: CacheDb
)  {
	fun create(query: String): AlbumPagingSource = AlbumPagingSource(query, api, searchResponseNetworkMapper, albumInAListCacheCacheMapper, db)
}
