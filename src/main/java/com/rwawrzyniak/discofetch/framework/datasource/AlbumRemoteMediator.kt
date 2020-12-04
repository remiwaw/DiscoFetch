package com.rwawrzyniak.discofetch.framework.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.rwawrzyniak.discofetch.business.data.cache.abstraction.CacheDb
import com.rwawrzyniak.discofetch.business.data.network.abstraction.DiscogsNetworkDataSource
import com.rwawrzyniak.discofetch.business.domain.model.Album
import com.rwawrzyniak.discofetch.framework.datasource.cache.model.AlbumRemoteKeyEntity
import com.rwawrzyniak.discofetch.framework.datasource.network.implementation.NetworkMapper
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

// https://www.bornfight.com/blog/android-paging-3-library-with-page-and-limit-parameters/
@OptIn(ExperimentalPagingApi::class)
class AlbumRemoteMediator(
	private val initialPage: Int = 1,
	private val db: CacheDb,
	private val api: DiscogsNetworkDataSource,
	private val networkMapper: NetworkMapper
) : RemoteMediator<Int, Album>() {
	override suspend fun load(loadType: LoadType, state: PagingState<Int, Album>): MediatorResult {
// calculate the current page to load depending on the state

		return try {
			val page = when (loadType) {
				LoadType.REFRESH -> {
					val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
					remoteKeys?.nextKey?.minus(1) ?: initialPage
				}
				LoadType.PREPEND -> {
					return MediatorResult.Success(true)
				}
				LoadType.APPEND -> {
					val remoteKeys = getRemoteKeyForLastItem(state)
						?: throw InvalidObjectException("Result is empty")
					remoteKeys.nextKey ?: return MediatorResult.Success(true)
				}
			}

			// load the list of items from API using calculated current page.
			// make sure the sort of the remote data and local data is the same!
			val response = api.loadAll(
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

				val prevKey = if (page == initialPage) null else page - 1
				val nextKey = if (endOfPaginationReached) null else page + 1

				val keys = response.searchResults.map {
					AlbumRemoteKeyEntity(albumId = it.id.toLong(), prevKey = prevKey, nextKey = nextKey)
				}
				val albums = response.searchResults.map(networkMapper::mapFromEntity)

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

	private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Album>): AlbumRemoteKeyEntity? {
		return state.lastItemOrNull()?.let { news ->
			db.withTransaction { db.getAlbumRemoteKeysDao().remoteKeysByNewsId(news.id) }
		}
	}
	private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Album>): AlbumRemoteKeyEntity? {
		return state.anchorPosition?.let { position ->
			state.closestItemToPosition(position)?.id?.let { id ->
				db.withTransaction { db.getAlbumRemoteKeysDao().remoteKeysByNewsId(id) }
			}
		}
	}
}

