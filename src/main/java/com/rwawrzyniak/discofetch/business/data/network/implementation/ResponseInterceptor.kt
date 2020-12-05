package com.rwawrzyniak.discofetch.business.data.network.implementation

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class ResponseInterceptor @Inject constructor() : Interceptor {
	override fun intercept(chain: Interceptor.Chain): Response {
		val response: Response = chain.proceed(chain.request())
		val rateLimitRemaining = response.header("x-discogs-ratelimit-remaining")?.toInt() ?: 5

		// We want to stop making request earlier, because of prefatching.
		 if(rateLimitRemaining < 5){
			Log.w(
				TAG,
				"You have exhausted your rate limit"
			)
			 Thread.sleep(5000) // Fix this, make better wait
		}

		return response
	}

	companion object {
		private const val TAG = "ResponseInterceptor"
	}
}
