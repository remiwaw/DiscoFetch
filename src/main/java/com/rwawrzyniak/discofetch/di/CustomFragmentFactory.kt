package com.rwawrzyniak.discofetch.di

import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.paging.ExperimentalPagingApi
import com.rwawrzyniak.discofetch.presentation.albumslist.AlbumListFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class CustomFragmentFactory @Inject constructor(
	private val inputMethodManager: InputMethodManager
) : FragmentFactory() {
	@OptIn(ExperimentalPagingApi::class)
	override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
		return when (className) {
			AlbumListFragment::class.java.name -> AlbumListFragment(inputMethodManager)
			else -> super.instantiate(classLoader, className)
		}
	}
}
