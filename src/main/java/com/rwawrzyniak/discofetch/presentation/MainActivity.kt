package com.rwawrzyniak.discofetch.presentation

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.rwawrzyniak.discofetch.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main){

	@Inject
	lateinit var inputMethodManager: InputMethodManager

	override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
		super.onCreate(savedInstanceState, persistentState)
		getNavController().navigate(R.id.searchFragment)
	}

	private fun getNavController(): NavController {
		val navHostFragment =
			supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
		return navHostFragment.navController
	}

	// This clear focus for all edit texts, when clicked outside.
	override fun dispatchTouchEvent(event: MotionEvent): Boolean {
		if (event.action == MotionEvent.ACTION_DOWN) {
			val v: View? = currentFocus
			if (v is EditText) {
				val outRect = Rect()
				v.getGlobalVisibleRect(outRect)
				if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
					v.clearFocus()
					inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0)
				}
			}
		}
		return super.dispatchTouchEvent(event)
	}
}
