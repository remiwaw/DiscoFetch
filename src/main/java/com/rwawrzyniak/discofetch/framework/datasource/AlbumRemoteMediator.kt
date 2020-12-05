package com.rwawrzyniak.discofetch.framework.datasource

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.rwawrzyniak.discofetch.business.data.cache.abstraction.CacheDb
import com.rwawrzyniak.discofetch.business.data.network.abstraction.DiscogsNetworkDataSource
import com.rwawrzyniak.discofetch.business.data.network.implementation.SearchResponse
import com.rwawrzyniak.discofetch.business.data.network.implementation.SearchResult
import com.rwawrzyniak.discofetch.framework.datasource.cache.model.AlbumCacheEntity
import com.rwawrzyniak.discofetch.framework.datasource.cache.model.AlbumRemoteKeyEntity
import com.rwawrzyniak.discofetch.framework.datasource.network.implementation.NetworkMapper
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException
import javax.inject.Inject

// https://www.bornfight.com/blog/android-paging-3-library-with-page-and-limit-parameters/
@OptIn(ExperimentalPagingApi::class)
class AlbumRemoteMediator @Inject constructor(
	private val db: CacheDb,
	private val api: DiscogsNetworkDataSource,
	private val networkMapper: NetworkMapper
) : RemoteMediator<Int, AlbumCacheEntity>() {
	private val initialPage: Int = 1

	override suspend fun load(loadType: LoadType, state: PagingState<Int, AlbumCacheEntity>): MediatorResult {
// calculate the current page to load depending on the state

		return try {
			val page = when (loadType) {
				LoadType.REFRESH -> {
					val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
					Log.i("WTFF", "REFRESH REmote key: $remoteKeys")
					remoteKeys?.nextKey?.minus(1) ?: initialPage
				}
				LoadType.PREPEND -> {
					Log.i("WTFF", "PREPEND:")
					return MediatorResult.Success(true)
				}
				LoadType.APPEND -> {
					val remoteKeys = getRemoteKeyForLastItem(state)
						?: throw InvalidObjectException("Result is empty")
					Log.i("WTFF", "APPEND REmote key: $remoteKeys")
					remoteKeys.nextKey ?: return MediatorResult.Success(true)
				}
			}

			// load the list of items from API using calculated current page.
			// make sure the sort of the remote data and local data is the same!

			val response : SearchResponse = api.loadAll(
					perPage = state.config.pageSize,
					page = page
				)


			// add custom logic, if you have some API metadata, you can use it as well
			val endOfPaginationReached = response.pagination.urls.next.isNullOrEmpty()

			db.withTransaction {
				// if refreshing, clear table and start over
				if (loadType == LoadType.REFRESH) {
					db.getAlbumRemoteKeysDao().clearRemoteKeys()
					db.getAlbumDao().deleteAlbumsItems()
				}

				Log.i("WTFF", "Page:: $page" )
				val prevKey = if (page == initialPage) null else page - 1
				val nextKey = if (endOfPaginationReached) null else page + 1

				val searchResultList: List<SearchResult> = response.results.sortedBy { it.id }
				val albums: List<AlbumCacheEntity> = searchResultList.map { networkMapper.mapFromEntity(it) }
				val keys = albums.map {
					AlbumRemoteKeyEntity(albumId = it.id, prevKey = prevKey, nextKey = nextKey)
				}

				db.getAlbumRemoteKeysDao().insertAll(keys)
				db.getAlbumDao().insertAll(albums)
			}

			MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
		} catch (e: IOException) {
			MediatorResult.Error(e)
		} catch (e: HttpException) {
			MediatorResult.Error(e)
		}
	}

	private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, AlbumCacheEntity>): AlbumRemoteKeyEntity? {
		return state.lastItemOrNull()?.let { album ->
			Log.i("WTFF", "getRemoteKeyForLastItem: $album")

			db.withTransaction { db.getAlbumRemoteKeysDao().remoteKeysByAlbumId(album.id) }
		}
	}
	private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, AlbumCacheEntity>): AlbumRemoteKeyEntity? {
		return state.anchorPosition?.let { position ->
			Log.i("WTFF", "getRemoteKeyClosestToCurrentPosition: $position")

			state.closestItemToPosition(position)?.id?.let { id ->
				db.withTransaction { db.getAlbumRemoteKeysDao().remoteKeysByAlbumId(id) }
			}
		}
	}
}

