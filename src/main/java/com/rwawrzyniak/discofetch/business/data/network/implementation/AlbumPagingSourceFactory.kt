package com.rwawrzyniak.discofetch.business.data.network.implementation

import androidx.paging.ExperimentalPagingApi
import com.rwawrzyniak.discofetch.business.data.cache.abstraction.CacheDb
import com.rwawrzyniak.discofetch.business.data.cache.mappers.CacheMapper
import com.rwawrzyniak.discofetch.business.data.network.abstraction.DiscogsNetworkDataSource
import com.rwawrzyniak.discofetch.business.data.network.mappers.NetworkMapper
import javax.inject.Inject

@ExperimentalPagingApi
class AlbumPagingSourceFactory @ExperimentalPagingApi
@Inject constructor(private val api: DiscogsNetworkDataSource,
					private val networkMapper: NetworkMapper,
					private val cacheMapper: CacheMapper,
					private val db: CacheDb
)  {
	fun create(query: String): AlbumPagingSource = AlbumPagingSource(query, api, networkMapper, cacheMapper, db)
}
