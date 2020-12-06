package com.rwawrzyniak.discofetch.business.data.network.implementation

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rwawrzyniak.discofetch.business.data.network.abstraction.DiscogsNetworkDataSource
import com.rwawrzyniak.discofetch.business.data.network.implementation.NetworkConstants.PAGE_SIZE
import com.rwawrzyniak.discofetch.business.data.network.mappers.GetArtistByIdNetworkMapper
import com.rwawrzyniak.discofetch.business.data.network.mappers.GetReleaseByIdNetworkMapper
import com.rwawrzyniak.discofetch.business.domain.model.AlbumDetails
import com.rwawrzyniak.discofetch.business.domain.model.AlbumInAList
import com.rwawrzyniak.discofetch.business.domain.model.ArtistDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class AlbumRepository @ExperimentalPagingApi
@Inject constructor(
	// TODO Move here DB persistance, for later caching
	private val albumPagingSourceFactory: AlbumPagingSourceFactory,
	private val discogsNetworkDataSource: DiscogsNetworkDataSource,
	private val getReleaseByIdNetworkMapper: GetReleaseByIdNetworkMapper,
	private val getArtistByIdNetworkMapper: GetArtistByIdNetworkMapper,
	) {
	suspend fun getArtistById(artistId: Long): ArtistDetails =
		getArtistByIdNetworkMapper.mapFromEntity(discogsNetworkDataSource.getArtistById(artistId))

	suspend fun getAlbumById(releaseId: Long): AlbumDetails =
		getReleaseByIdNetworkMapper.mapFromEntity(discogsNetworkDataSource.getReleaseById(releaseId))

	fun getSearchResultStream(query: String): Flow<PagingData<AlbumInAList>> {
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
