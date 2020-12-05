package com.rwawrzyniak.discofetch.framework.presentation.albumslist

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rwawrzyniak.discofetch.business.data.network.implementation.AlbumRepository
import com.rwawrzyniak.discofetch.business.domain.model.Album
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class AlbumListViewModel @ExperimentalPagingApi
@ViewModelInject constructor(
	private val repository: AlbumRepository,
	@Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

	fun searchRepo(queryString: String): Flow<PagingData<Album>> {
		return repository.getSearchResultStream(queryString)
			.cachedIn(viewModelScope)
	}
}

