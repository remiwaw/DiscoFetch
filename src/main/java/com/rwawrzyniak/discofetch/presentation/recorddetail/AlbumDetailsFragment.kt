package com.rwawrzyniak.discofetch.presentation.recorddetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.rwawrzyniak.discofetch.R
import com.rwawrzyniak.discofetch.business.domain.model.Artist
import com.rwawrzyniak.discofetch.databinding.FragmentAlbumDetailsBinding
import com.rwawrzyniak.discofetch.presentation.recorddetail.state.AlbumDetailsIntent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
@ExperimentalPagingApi
class AlbumDetailsFragment : Fragment(R.layout.fragment_album_details) {
	private val args: AlbumDetailsFragmentArgs by navArgs()
	private val viewModel: AlbumDetailsViewModel by viewModels()
	private val artistAdapter : ArrayAdapter<Artist> by lazy { ArtistListAdapter(requireContext())}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val binding: FragmentAlbumDetailsBinding = FragmentAlbumDetailsBinding.inflate(
			inflater,
			container,
			false
		).apply {
			lifecycleOwner = viewLifecycleOwner
			vm = viewModel
		}
		binding.artists.adapter = artistAdapter
		binding.artists.onItemClickListener = OnItemClickListener { parent, _, position, _ ->
			val selectedArtist = parent.getItemAtPosition(position) as Artist
			findNavController().navigate(AlbumDetailsFragmentDirections.actionAlbumDetailsFragmentToArtistDetailsFragment(selectedArtist.id))
		}
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel.send(AlbumDetailsIntent.LoadAlbum(args.albumId, args.imageUrl))
	}



}
