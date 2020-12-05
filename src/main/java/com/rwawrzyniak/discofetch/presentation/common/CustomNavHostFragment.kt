package com.rwawrzyniak.discofetch.presentation.common

import android.content.Context
import androidx.navigation.fragment.NavHostFragment
import com.rwawrzyniak.discofetch.di.CustomFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CustomNavHostFragment : NavHostFragment() {

	@Inject
	lateinit var fragmentFactory: CustomFragmentFactory

	override fun onAttach(context: Context) {
		super.onAttach(context)
		childFragmentManager.fragmentFactory = fragmentFactory
	}

}
