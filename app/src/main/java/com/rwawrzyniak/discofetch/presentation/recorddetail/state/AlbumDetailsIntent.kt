package com.rwawrzyniak.discofetch.presentation.recorddetail.state

sealed class AlbumDetailsIntent {
    data class LoadAlbum(val albumId: Long, val imageUrl: String) : AlbumDetailsIntent()
}
