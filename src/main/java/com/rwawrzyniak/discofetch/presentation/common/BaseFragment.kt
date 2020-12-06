package com.rwawrzyniak.discofetch.presentation.common

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class BaseFragment(fragmentLayout: Int) : Fragment(fragmentLayout) {

	private val actionBar by lazy { (activity as ChromeExtensionsProvider).getAppActionBar() }

    open fun getChromeConfig(): ChromeConfiguration =
		ChromeConfiguration()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
	}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureChrome(getChromeConfig())
    }
    // endregion


	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		val id = item.itemId
		if (id == android.R.id.home) {
			goBack()
			return true
		}
		return super.onOptionsItemSelected(item)
	}

	private fun goBack() {
		findNavController().popBackStack()
	}

	// region Private Methods
    private fun configureChrome(config: ChromeConfiguration) {
        setupActionBar(config)
        updateHomeAsUpButtonState(config.showActionBarUpButton)
    }

    private fun setupActionBar(config: ChromeConfiguration) {
        with(actionBar) {
            if (config.showActionBar) {
                show()
                title = config.actionBarTitle
            } else {
                hide()
            }
        }
    }

    private fun updateHomeAsUpButtonState(shouldShowUpButton: Boolean) {
		actionBar.setDisplayHomeAsUpEnabled(shouldShowUpButton)
    }
}
