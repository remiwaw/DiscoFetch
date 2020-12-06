package com.rwawrzyniak.discofetch.business.data.network.interceptors

import com.rwawrzyniak.discofetch.business.data.network.implementation.NetworkConstants.TOKEN
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject


class RequestInterceptor @Inject constructor() : Interceptor {
	override fun intercept(chain: Interceptor.Chain): Response {
		var request: Request = chain.request()
		val url: HttpUrl = request.url.newBuilder().addQueryParameter(TOKEN.first, TOKEN.second).build()
		request = request.newBuilder().url(url).build()
		return chain.proceed(request)
	}
}
