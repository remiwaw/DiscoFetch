package com.rwawrzyniak.discofetch.presentation.artistdetail.state

sealed class ArtistDetailsIntent {
    data class LoadArtist(val artistId: Long) : ArtistDetailsIntent()
}
