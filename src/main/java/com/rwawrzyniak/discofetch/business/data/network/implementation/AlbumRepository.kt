package com.rwawrzyniak.discofetch.business.data.network.implementation

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rwawrzyniak.discofetch.business.data.network.implementation.NetworkConstants.PAGE_SIZE
import com.rwawrzyniak.discofetch.business.domain.model.Album
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class AlbumRepository @ExperimentalPagingApi
@Inject constructor(
	// TODO Move here DB persistance, for later caching
	private val albumPagingSourceFactory: AlbumPagingSourceFactory
) {
	fun getSearchResultStream(query: String): Flow<PagingData<Album>> {
		val config = PagingConfig(
			pageSize = PAGE_SIZE,
			initialLoadSize = PAGE_SIZE,
			prefetchDistance = PAGE_SIZE,
			enablePlaceholders = true
		)

		return Pager(
			config = config,
			pagingSourceFactory = { albumPagingSourceFactory.create(query) },
		).flow
	}
}
