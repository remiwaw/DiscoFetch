package com.rwawrzyniak.discofetch.presentation.recorddetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.rwawrzyniak.discofetch.R
import com.rwawrzyniak.discofetch.databinding.FragmentAlbumDetailsBinding
import com.rwawrzyniak.discofetch.presentation.recorddetail.state.AlbumDetailsIntent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalPagingApi
class AlbumDetailsFragment : Fragment(R.layout.fragment_album_details) {
	private val args: AlbumDetailsFragmentArgs by navArgs()
	private val viewModel: AlbumDetailsViewModel by viewModels()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return FragmentAlbumDetailsBinding.inflate(
			inflater,
			container,
			false
		).apply {
			lifecycleOwner = viewLifecycleOwner
			vm = viewModel
		}.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel.send(AlbumDetailsIntent.LoadAlbum(args.albumId, args.imageUrl))
	}

}
