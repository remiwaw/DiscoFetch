package com.rwawrzyniak.discofetch.business.data.network.implementation

import androidx.paging.*
import com.rwawrzyniak.discofetch.business.data.cache.abstraction.CacheDb
import com.rwawrzyniak.discofetch.business.data.network.abstraction.DiscogsNetworkDataSource
import com.rwawrzyniak.discofetch.business.data.cache.mappers.AlbumInAListCacheCacheMapper
import com.rwawrzyniak.discofetch.business.data.network.mappers.SearchResponseNetworkMapper
import com.rwawrzyniak.discofetch.business.domain.model.AlbumInAList
import retrofit2.HttpException
import java.io.IOException

private const val DEFAULT_PAGE_INDEX = 1

@ExperimentalPagingApi
class AlbumPagingSource constructor(
	private val query: String,
	private val api: DiscogsNetworkDataSource,
	private val searchResponseNetworkMapper: SearchResponseNetworkMapper,
	private val albumInAListCacheCacheMapper: AlbumInAListCacheCacheMapper,
	private val db: CacheDb
) :
	PagingSource<Int, AlbumInAList>() {

	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AlbumInAList> {
		//for first case it will be null, then we can pass some default value, in our case it's 1
		val page = params.key ?: DEFAULT_PAGE_INDEX
		return try {
			val apiResponse = api.searchByAlbumTitle(albumName = query, perPage = params.loadSize, page = page)

			val albumsEntity = apiResponse.results.map { searchResponseNetworkMapper.mapFromEntity(it) }

			// TODO implement caching
//			db.getAlbumDao().insertAll(albumsEntity)

			val albumInALists: List<AlbumInAList> = albumsEntity.map { albumInAListCacheCacheMapper.mapFromEntity(it) }

			LoadResult.Page(
				albumInALists,
				prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
				nextKey = if (albumsEntity.isEmpty()) null else page + 1
			)

		} catch (exception: IOException) {
			return LoadResult.Error(exception)
		} catch (exception: HttpException) {
			return LoadResult.Error(exception)
		}
	}

}
