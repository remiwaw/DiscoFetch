package com.rwawrzyniak.discofetch.framework.presentation.albumslist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import com.rwawrzyniak.discofetch.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_album_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.ldralighieri.corbind.widget.textChanges

@ExperimentalPagingApi
@AndroidEntryPoint
class AlbumListFragment : Fragment(R.layout.fragment_album_list) {

	private val viewModel: AlbumListViewModel by viewModels()
	private val albumsAdapter: AlbumsAdapter by lazy { AlbumsAdapter() }

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		(activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
		setHasOptionsMenu(true)
		setupUI()
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
