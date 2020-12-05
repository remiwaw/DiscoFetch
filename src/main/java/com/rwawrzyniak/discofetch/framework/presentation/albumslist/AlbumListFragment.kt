package com.rwawrzyniak.discofetch.framework.presentation.albumslist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.rwawrzyniak.discofetch.R
import com.rwawrzyniak.discofetch.business.domain.model.Album
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_album_list.*

@AndroidEntryPoint
class AlbumListFragment : Fragment(R.layout.fragment_album_list) {

	private val viewModel: AlbumListViewModel by viewModels()
	private val albumsAdapter: AlbumsAdapter by lazy { AlbumsAdapter() }

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		(activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
		setHasOptionsMenu(true)
		setupUI()

		initRecyclerView()
	}

	private fun setupUI() {
		with(albums_recyclerview) {
			layoutManager = GridLayoutManager(requireContext(), 2)
			adapter = albumsAdapter
		}
	}

	private fun initRecyclerView(){
		viewModel.fetchAlbums().observe(viewLifecycleOwner) { pagingData: PagingData<Album> ->
			albumsAdapter.submitData(
				viewLifecycleOwner.lifecycle,
				pagingData
			)
		}
	}
}
