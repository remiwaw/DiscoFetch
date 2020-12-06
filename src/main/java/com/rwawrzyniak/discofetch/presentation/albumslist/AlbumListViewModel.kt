package com.rwawrzyniak.discofetch.presentation.albumslist

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rwawrzyniak.discofetch.business.data.network.implementation.AlbumRepository
import com.rwawrzyniak.discofetch.business.domain.model.AlbumDetails
import com.rwawrzyniak.discofetch.business.domain.model.AlbumInAList
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class AlbumListViewModel @ExperimentalPagingApi
@ViewModelInject constructor(
	private val repository: AlbumRepository,
	@Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

	private var currentQueryValue: String? = null
	private var currentSearchResult: Flow<PagingData<AlbumInAList>>? = null

	fun searchRepo(queryString: String): Flow<PagingData<AlbumInAList>> {
		val lastResult = currentSearchResult
		if (queryString == currentQueryValue && lastResult != null) {
			return lastResult
		}
		currentQueryValue = queryString

		val newResult = repository.getSearchResultStream(queryString)
			.cachedIn(viewModelScope)

		currentSearchResult = newResult
		return newResult
	}
}

