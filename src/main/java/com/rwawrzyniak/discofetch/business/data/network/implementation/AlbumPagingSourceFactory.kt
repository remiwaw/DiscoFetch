package com.rwawrzyniak.discofetch.business.data.network.implementation

import androidx.paging.ExperimentalPagingApi
import com.rwawrzyniak.discofetch.business.data.cache.abstraction.CacheDb
import com.rwawrzyniak.discofetch.business.data.network.abstraction.DiscogsNetworkDataSource
import com.rwawrzyniak.discofetch.framework.datasource.AlbumPagingSource
import com.rwawrzyniak.discofetch.framework.datasource.cache.mappers.CacheMapper
import com.rwawrzyniak.discofetch.framework.datasource.network.implementation.NetworkMapper
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
