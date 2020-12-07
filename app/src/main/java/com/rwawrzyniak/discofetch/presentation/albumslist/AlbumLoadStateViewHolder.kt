package com.rwawrzyniak.discofetch.presentation.albumslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.rwawrzyniak.discofetch.R
import com.rwawrzyniak.discofetch.databinding.AlbumsLoadStateFooterViewItemBinding

class AlbumLoadStateViewHolder(
	private val binding: AlbumsLoadStateFooterViewItemBinding,
	retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState !is LoadState.Loading
        binding.errorMsg.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): AlbumLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.albums_load_state_footer_view_item, parent, false)
            val binding = AlbumsLoadStateFooterViewItemBinding.bind(view)
            return AlbumLoadStateViewHolder(binding, retry)
        }
    }
}
