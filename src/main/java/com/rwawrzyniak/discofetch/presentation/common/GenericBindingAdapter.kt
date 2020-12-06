package com.rwawrzyniak.discofetch.presentation.common

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.databinding.BindingAdapter
import coil.api.load

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
	// TODO take care of disposal to avoid memory leaks
	if (!url.isNullOrEmpty()) {
		view.load(url)
	}
}

@BindingAdapter("hideOnLoading")
fun Group.hideOnLoading(responseState: UIState<Any>) {
	visibility = if (responseState is UIState.Loading)
		View.GONE
	else
		View.VISIBLE
}

@BindingAdapter("showOnLoading")
fun ProgressBar.showOnLoading(responseState: UIState<Any>) {
	visibility = if (responseState is UIState.Loading)
		View.VISIBLE
	else
		View.GONE
}

@BindingAdapter("showOnError")
fun TextView.showError(responseState: UIState<Any>) {
	visibility = if (responseState is UIState.Error) {
		text = responseState.errorMessage
		View.VISIBLE
	} else
		View.GONE
}
