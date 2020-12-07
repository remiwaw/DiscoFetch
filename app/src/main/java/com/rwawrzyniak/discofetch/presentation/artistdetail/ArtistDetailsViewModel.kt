package com.rwawrzyniak.discofetch.presentation.artistdetail

import android.text.SpannableStringBuilder
import androidx.core.text.toSpannable
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import com.rwawrzyniak.discofetch.business.data.network.implementation.AlbumRepository
import com.rwawrzyniak.discofetch.business.domain.model.ArtistDetails
import com.rwawrzyniak.discofetch.presentation.artistdetail.state.ArtistDetailViewState
import com.rwawrzyniak.discofetch.presentation.artistdetail.state.ArtistDetailsIntent
import com.rwawrzyniak.discofetch.presentation.common.UIState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class ArtistDetailsViewModel @ExperimentalPagingApi
@ViewModelInject constructor(
	private val repository: AlbumRepository,
	@Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

	fun send(intent: ArtistDetailsIntent) = viewModelScope.launch { artistDetailsIntent.send(intent) }

	private val artistDetailsIntent = Channel<ArtistDetailsIntent>(Channel.UNLIMITED)
	private val _state = MutableStateFlow<UIState<ArtistDetailViewState>>(UIState.Idle)
	val viewState: LiveData<UIState<ArtistDetailViewState>>
		get() = _state.asLiveData()

	init {
		handleIntent()
	}

	private fun handleIntent() {
		viewModelScope.launch {
			artistDetailsIntent.consumeAsFlow().collectLatest { intent ->
				when(intent){
					is ArtistDetailsIntent.LoadArtist -> getArtist(intent.artistId)
				}
			}
		}
	}

	private suspend fun getArtist(artistId: Long) {
		_state.value = UIState.Loading
		_state.value = try {
			val artistById = repository.getArtistById(artistId)
			UIState.Success(mapToViewState(artistById))
		} catch (e: Exception) {
			UIState.Error(e.message ?: "Unknown error")
		}
	}

	// TODO this should be in use case
	private fun mapToViewState(aristDetails: ArtistDetails): ArtistDetailViewState {
		val linksSpannableStringBuilder = SpannableStringBuilder()
		aristDetails.urls.forEach{
			linksSpannableStringBuilder.appendLine(it)
		}

		return ArtistDetailViewState(
			name = aristDetails.name,
			profile = aristDetails.profile,
			urls = linksSpannableStringBuilder.toSpannable()
		)
	}
}

