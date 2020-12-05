package com.rwawrzyniak.discofetch.framework.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.rwawrzyniak.discofetch.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main){

	override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
		super.onCreate(savedInstanceState, persistentState)
		getNavController().navigate(R.id.searchFragment)
	}

	private fun getNavController(): NavController {
		val navHostFragment =
			supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
		return navHostFragment.navController
	}
}
