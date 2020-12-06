package com.rwawrzyniak.discofetch.presentation.albumslist

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.rwawrzyniak.discofetch.R
import com.rwawrzyniak.discofetch.business.domain.model.AlbumInAList
import com.rwawrzyniak.discofetch.databinding.FragmentAlbumListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_album_list.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.ldralighieri.corbind.widget.textChanges

@ExperimentalPagingApi
@AndroidEntryPoint
class AlbumListFragment : Fragment(R.layout.fragment_album_list) {

	private lateinit var binding: FragmentAlbumListBinding

	private val viewModel: AlbumListViewModel by viewModels()

	private val albumsAdapter: AlbumsAdapter by lazy { AlbumsAdapter(::onItemClicked).apply {
		withLoadStateHeaderAndFooter(
			header = AlbumsLoadStateAdapter { albumsAdapter.retry() },
			footer = AlbumsLoadStateAdapter { albumsAdapter.retry() }
		)
		addLoadStateListener { loadState ->
			binding.albumsRecyclerview.isVisible = loadState.source.refresh is LoadState.NotLoading
			binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
			binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

			val errorState = loadState.source.append as? LoadState.Error
				?: loadState.source.prepend as? LoadState.Error
				?: loadState.append as? LoadState.Error
				?: loadState.prepend as? LoadState.Error
			errorState?.let {
				Toast.makeText(
					requireContext(),
					"Error ${it.error}",
					Toast.LENGTH_LONG
				).show()
			}
		}
	}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = FragmentAlbumListBinding.inflate(layoutInflater)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		(activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
		setHasOptionsMenu(true)
		setupUI()
	}

	private fun onItemClicked(albumInAList: AlbumInAList){
		findNavController().navigate(AlbumListFragmentDirections.actionSearchFragmentToAlbumDetailsFragment(albumInAList.id, albumInAList.cover_image))
	}

	private fun setupUI() {
		lifecycleScope.launch {
			search_input
				.textChanges()
				.debounce(2000)
				.collectLatest { charSequence ->
					Log.i("ASASSA", charSequence.toString())
					viewModel.searchRepo(charSequence.toString().trim()).collectLatest { pagingData ->
						albumsAdapter.submitData(pagingData)
					}
				}
		}

		with(albums_recyclerview) {
			layoutManager = GridLayoutManager(requireContext(), 2)
			adapter = albumsAdapter
		}
	}
}
