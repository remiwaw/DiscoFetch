package com.rwawrzyniak.discofetch.presentation.recorddetail

import android.text.SpannableString
import android.text.SpannableStringBuilder
import androidx.core.text.toSpannable
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import com.rwawrzyniak.discofetch.business.data.network.implementation.AlbumRepository
import com.rwawrzyniak.discofetch.business.domain.model.AlbumDetails
import com.rwawrzyniak.discofetch.presentation.common.UIState
import com.rwawrzyniak.discofetch.presentation.recorddetail.state.AlbumDetailViewState
import com.rwawrzyniak.discofetch.presentation.recorddetail.state.AlbumDetailsIntent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class AlbumDetailsViewModel @ExperimentalPagingApi
@ViewModelInject constructor(
	private val repository: AlbumRepository,
	@Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

	fun send(intent: AlbumDetailsIntent) = viewModelScope.launch { albumDetailsIntent.send(intent) }

	private val albumDetailsIntent = Channel<AlbumDetailsIntent>(Channel.UNLIMITED)
	private val _state = MutableStateFlow<UIState<AlbumDetailViewState>>(UIState.Idle)
	val viewState: LiveData<UIState<AlbumDetailViewState>>
		get() = _state.asLiveData()

	init {
		handleIntent()
	}

	private fun handleIntent() {
		viewModelScope.launch {
			albumDetailsIntent.consumeAsFlow().collectLatest { intent ->
				when(intent){
					is AlbumDetailsIntent.LoadAlbum -> getAlbum(intent.albumId, intent.imageUrl)
				}
			}
		}
	}

	private suspend fun getAlbum(albumId: Long, imageUrl: String) {
			_state.value = UIState.Loading
			_state.value = try {
				val albumById = repository.getAlbumById(albumId)
				albumById.coverImageUrl = imageUrl
				UIState.Success(mapToViewState(albumById))

			} catch (e: Exception) {
				UIState.Error(e.message ?: "Unknown error")
			}
		}

	// TODO this should be in use case
	private fun mapToViewState(albumDetails: AlbumDetails): AlbumDetailViewState {
		val tracksSpannableStringBuilder = SpannableStringBuilder()
		albumDetails.tracks.forEach{
			tracksSpannableStringBuilder.appendLine(it)
		}

		return AlbumDetailViewState(
			cover_image_url = albumDetails.coverImageUrl,
			title = albumDetails.title,
			releasedYear = SpannableString(albumDetails.releasedYear),
			artists = albumDetails.artists,
			tracks = tracksSpannableStringBuilder.toSpannable()
		)
	}

}

