package com.rwawrzyniak.discofetch.presentation.artistdetail.state

import android.text.Spannable

data class ArtistDetailViewState(
	val name: String,
	val profile: String,
	val urls: Spannable
)
