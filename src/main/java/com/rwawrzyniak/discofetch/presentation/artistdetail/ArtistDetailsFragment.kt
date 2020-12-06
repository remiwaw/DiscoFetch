package com.rwawrzyniak.discofetch.presentation.artistdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.rwawrzyniak.discofetch.R
import com.rwawrzyniak.discofetch.databinding.FragmentArtistDetailsBinding
import com.rwawrzyniak.discofetch.presentation.artistdetail.state.ArtistDetailsIntent
import com.rwawrzyniak.discofetch.presentation.common.BaseFragment
import com.rwawrzyniak.discofetch.presentation.common.ChromeConfiguration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalPagingApi
class ArtistDetailsFragment : BaseFragment(R.layout.fragment_artist_details) {
	private val args: ArtistDetailsFragmentArgs by navArgs()
	private val viewModel: ArtistDetailsViewModel by viewModels()

	override fun getChromeConfig(): ChromeConfiguration =
		ChromeConfiguration(
			actionBarTitle = getString(R.string.aristDetailsFragmentTitle),
		)

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return FragmentArtistDetailsBinding.inflate(
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
		viewModel.send(ArtistDetailsIntent.LoadArtist(args.artistId))
	}

}
