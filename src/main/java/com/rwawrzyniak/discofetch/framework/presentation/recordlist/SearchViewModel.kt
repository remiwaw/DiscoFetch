package com.rwawrzyniak.discofetch.framework.presentation.recordlist

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.rwawrzyniak.discofetch.business.data.DiscogsSearchRepository
import kotlinx.coroutines.runBlocking

class SearchViewModel @ViewModelInject constructor(
	private val repository: DiscogsSearchRepository,
	@Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

	fun launch(){
		runBlocking { repository.searchByAlbumName("nevermind") }
	}
}
