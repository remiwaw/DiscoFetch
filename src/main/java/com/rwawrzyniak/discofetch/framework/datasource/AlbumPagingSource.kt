package com.rwawrzyniak.discofetch.framework.datasource

import androidx.paging.*
import com.rwawrzyniak.discofetch.business.data.cache.abstraction.CacheDb
import com.rwawrzyniak.discofetch.business.data.network.abstraction.DiscogsNetworkDataSource
import com.rwawrzyniak.discofetch.business.domain.model.Album
import com.rwawrzyniak.discofetch.framework.datasource.cache.mappers.CacheMapper
import com.rwawrzyniak.discofetch.framework.datasource.cache.model.AlbumCacheEntity
import com.rwawrzyniak.discofetch.framework.datasource.network.implementation.NetworkMapper
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val DEFAULT_PAGE_INDEX = 1

@ExperimentalPagingApi
class AlbumPagingSource constructor(
	private val query: String,
	private val api: DiscogsNetworkDataSource,
	private val networkMapper: NetworkMapper,
	private val cacheMapper: CacheMapper,
	private val db: CacheDb
) :
	PagingSource<Int, Album>() {

	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Album> {
		//for first case it will be null, then we can pass some default value, in our case it's 1
		val page = params.key ?: DEFAULT_PAGE_INDEX
		return try {
			val apiResponse = api.searchByAlbum(albumName = query, perPage = params.loadSize, page = page)

			val albumsEntity = apiResponse.results.map { networkMapper.mapFromEntity(it) }

			db.getAlbumDao().insertAll(albumsEntity)

			val albums: List<Album> = albumsEntity.map { cacheMapper.mapFromEntity(it) }

			LoadResult.Page(
				albums,
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
