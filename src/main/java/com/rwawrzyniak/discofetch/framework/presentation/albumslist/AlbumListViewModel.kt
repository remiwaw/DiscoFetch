package com.rwawrzyniak.discofetch.framework.presentation.albumslist

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.paging.*
import com.rwawrzyniak.discofetch.business.data.network.implementation.NetworkConstants.PAGE_SIZE
import com.rwawrzyniak.discofetch.business.domain.model.Album
import com.rwawrzyniak.discofetch.framework.datasource.AlbumRemoteMediator
import com.rwawrzyniak.discofetch.framework.datasource.cache.database.AlbumDao
import com.rwawrzyniak.discofetch.framework.datasource.cache.mappers.CacheMapper

class AlbumListViewModel @ViewModelInject constructor(
	private val albumRemoteMediator: AlbumRemoteMediator,
	private val albumDao: AlbumDao,
	private val mapper: CacheMapper,
	@Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

	private val loadStateListener = fun(combinedLoadStates: CombinedLoadStates) {
		when (val state = combinedLoadStates.source.refresh) {
			is LoadState.NotLoading -> onLoadingFinished()
			is LoadState.Error -> onLoadingError(state.error)
		}
	}

	private fun onLoadingFinished() {
		Log.i("TEST", "Finished")
	}

	private fun onLoadingError(error: Throwable) {
		Log.e("Terrible error", error.localizedMessage, error)
	}



	fun fetchAlbums(): LiveData<PagingData<Album>> = wirePagedList()

	private fun wirePagedList(): LiveData<PagingData<Album>> {
		val config = PagingConfig(
			pageSize = PAGE_SIZE,
			initialLoadSize = PAGE_SIZE,
			prefetchDistance = PAGE_SIZE,
			enablePlaceholders = true
		)

		return Pager(
			config = config,
			remoteMediator = albumRemoteMediator,
			pagingSourceFactory = { albumDao.getAll() }
		).liveData.map { it.map { albumCacheEntity -> mapper.mapFromEntity(albumCacheEntity) } }
	}
}

