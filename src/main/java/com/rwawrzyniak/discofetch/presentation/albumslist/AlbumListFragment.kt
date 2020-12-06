package com.rwawrzyniak.discofetch.presentation.albumslist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.rwawrzyniak.discofetch.R
import com.rwawrzyniak.discofetch.business.domain.model.AlbumInAList
import com.rwawrzyniak.discofetch.databinding.FragmentAlbumListBinding
import com.rwawrzyniak.discofetch.presentation.common.BaseFragment
import com.rwawrzyniak.discofetch.presentation.common.ChromeConfiguration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_album_list.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import ru.ldralighieri.corbind.widget.textChanges

@ExperimentalPagingApi
@AndroidEntryPoint
class AlbumListFragment : BaseFragment(R.layout.fragment_album_list) {
	private lateinit var binding: FragmentAlbumListBinding
	private val viewModel: AlbumListViewModel by viewModels()

	private val albumsAdapter: AlbumsAdapter by lazy { initAdapter() }

	override fun getChromeConfig(): ChromeConfiguration =
		ChromeConfiguration(
			showActionBar = false
		)

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentAlbumListBinding.inflate(
			inflater,
			container,
			false
		).apply {
			lifecycleOwner = viewLifecycleOwner
		}

		with(binding.albumsRecyclerview){
			layoutManager = GridLayoutManager(requireContext(), 2)
			adapter = albumsAdapter
		}

		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupUI()
	}

	override fun onResume() {
		super.onResume()
		clearBusyState()
	}

	private fun clearBusyState() {
		binding.progressBar.isVisible = false
		binding.retryButton.isVisible = false
	}

	private fun onItemClicked(albumInAList: AlbumInAList) {
		findNavController().navigate(
			AlbumListFragmentDirections.actionSearchFragmentToAlbumDetailsFragment(
				albumInAList.id,
				albumInAList.cover_image
			)
		)
	}

	private fun initAdapter(): AlbumsAdapter =
		AlbumsAdapter(::onItemClicked).apply {
			withLoadStateHeaderAndFooter(
				header = AlbumsLoadStateAdapter { albumsAdapter.retry() },
				footer = AlbumsLoadStateAdapter { albumsAdapter.retry() }
			)
			addLoadStateListener { loadState ->
				Log.i("LOADING_TAG", loadState.source.toString())
				binding.albumsRecyclerview.isVisible =
					loadState.source.refresh is LoadState.NotLoading
				binding.inputLayout.isVisible = loadState.source.refresh is LoadState.NotLoading
				binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
				binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

				showToastOnError(loadState)
			}
		}

	private fun showToastOnError(loadState: CombinedLoadStates) {
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

	private fun setupUI() {
		lifecycleScope.launch {
			search_input
				.textChanges()
				.filter { it.isNotBlank() }
				.debounce(1000) // Wait for end type 1 second before executing request.
				.collectLatest { charSequence ->
					viewModel.searchRepo(charSequence.toString().trim())
						.collectLatest { pagingData ->
							albumsAdapter.submitData(pagingData)
						}
				}
		}
	}
}
