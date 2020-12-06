package com.rwawrzyniak.discofetch.presentation

import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.rwawrzyniak.discofetch.R
import com.rwawrzyniak.discofetch.presentation.common.ChromeExtensionsProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), ChromeExtensionsProvider {

	@Inject
	lateinit var inputMethodManager: InputMethodManager

	override fun getAppActionBar(): ActionBar {
		setSupportActionBar(mainActionBar)
		return requireNotNull(supportActionBar)
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
