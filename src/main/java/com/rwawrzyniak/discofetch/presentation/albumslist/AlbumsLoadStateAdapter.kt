package com.rwawrzyniak.discofetch.presentation.albumslist

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class AlbumsLoadStateAdapter(
        private val retry: () -> Unit
) : LoadStateAdapter<AlbumLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: AlbumLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): AlbumLoadStateViewHolder {
        return AlbumLoadStateViewHolder.create(parent, retry)
    }
}
