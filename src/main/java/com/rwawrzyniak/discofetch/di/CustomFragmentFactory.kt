package com.rwawrzyniak.discofetch.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.paging.ExperimentalPagingApi
import com.rwawrzyniak.discofetch.presentation.albumslist.AlbumListFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
// TODO not really needed now
class CustomFragmentFactory @Inject constructor() : FragmentFactory() {
	override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
		return when (className) {
			AlbumListFragment::class.java.name -> AlbumListFragment()
			else -> super.instantiate(classLoader, className)
		}
	}
}
