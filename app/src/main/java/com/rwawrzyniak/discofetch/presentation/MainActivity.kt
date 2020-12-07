package com.rwawrzyniak.discofetch.presentation

import android.graphics.Rect
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rwawrzyniak.discofetch.R
import com.rwawrzyniak.discofetch.business.domain.util.NetworkManager
import com.rwawrzyniak.discofetch.presentation.common.ChromeExtensionsProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@FlowPreview
@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), ChromeExtensionsProvider {

	@Inject
	lateinit var inputMethodManager: InputMethodManager

	@Inject
	lateinit var networkManager: NetworkManager

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		lifecycleScope.launch {
			networkManager.observeConnectivity()
				.collectLatest{ isConnected ->
					if(isConnected.not()){
						showNoConnectivityDialog()
					}
				}
		}
	}

	private fun showNoConnectivityDialog(){
		MaterialDialog(this).apply {
			title(R.string.no_connectivity_title)
			.message(R.string.no_connectivity_explain)
		}.show()
	}

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
