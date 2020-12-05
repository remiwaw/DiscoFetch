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


private const val DISCO_START_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class AlbumRemoteMediator @Inject constructor(
	private val db: CacheDb,
	private val api: DiscogsNetworkDataSource,
	private val networkMapper: NetworkMapper
) : RemoteMediator<Int, AlbumCacheEntity>() {

	override suspend fun load(loadType: LoadType, state: PagingState<Int, AlbumCacheEntity>): MediatorResult {

		val page = when (loadType) {
			LoadType.REFRESH -> {
				val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
				remoteKeys?.nextKey?.minus(1) ?: DISCO_START_PAGE_INDEX
			}
			LoadType.PREPEND -> {
				val remoteKeys = getRemoteKeyForFirstItem(state)
					?: // The LoadType is PREPEND so some data was loaded before,
					// so we should have been able to get remote keys
					// If the remoteKeys are null, then we're an invalid state and we have a bug
					throw InvalidObjectException("Remote key and the prevKey should not be null")
				// If the previous key is null, then we can't request more data
				val prevKey = remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
				remoteKeys.prevKey
			}
			LoadType.APPEND -> {
				val remoteKeys = getRemoteKeyForLastItem(state)
				if (remoteKeys?.nextKey == null) {
					throw InvalidObjectException("Remote key should not be null for $loadType")
				}
				remoteKeys.nextKey
			}

		}

		try {
			val apiResponse = api.loadAll(perPage = state.config.pageSize, page = page)

			val albums = apiResponse.results.map { networkMapper.mapFromEntity(it) }
			val endOfPaginationReached = albums.isEmpty()
			db.withTransaction {
				// clear all tables in the database
				if (loadType == LoadType.REFRESH) {
					db.getAlbumRemoteKeysDao().clearRemoteKeys()
					db.getAlbumDao().deleteAlbumsItems()
				}
				val prevKey = if (page == DISCO_START_PAGE_INDEX) null else page - 1
				val nextKey = if (endOfPaginationReached) null else page + 1
				val keys = albums.map {
					AlbumRemoteKeyEntity(albumId = it.id, prevKey = prevKey, nextKey = nextKey)
				}
				db.getAlbumRemoteKeysDao().insertAll(keys)
				db.getAlbumDao().insertAll(albums)
			}
			return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
		} catch (exception: IOException) {
			return MediatorResult.Error(exception)
		} catch (exception: HttpException) {
			return MediatorResult.Error(exception)
		}
	}

	private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, AlbumCacheEntity>): AlbumRemoteKeyEntity? {
		// Get the last page that was retrieved, that contained items.
		// From that last page, get the last item
		return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
			?.let { repo ->
				// Get the remote keys of the last item retrieved
				db.getAlbumRemoteKeysDao().remoteKeysByAlbumId(repo.id)
			}
	}

	private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, AlbumCacheEntity>): AlbumRemoteKeyEntity? {
		// Get the first page that was retrieved, that contained items.
		// From that first page, get the first item
		return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
			?.let { repo ->
				// Get the remote keys of the first items retrieved
				db.getAlbumRemoteKeysDao().remoteKeysByAlbumId(repo.id)
			}
	}

	private suspend fun getRemoteKeyClosestToCurrentPosition(
		state: PagingState<Int, AlbumCacheEntity>
	): AlbumRemoteKeyEntity? {
		// The paging library is trying to load data after the anchor position
		// Get the item closest to the anchor position
		return state.anchorPosition?.let { position ->
			state.closestItemToPosition(position)?.id?.let { repoId ->
				db.getAlbumRemoteKeysDao().remoteKeysByAlbumId(repoId)
			}
		}
	}
}

