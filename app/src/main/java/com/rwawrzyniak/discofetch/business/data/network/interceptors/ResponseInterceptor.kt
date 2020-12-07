package com.rwawrzyniak.discofetch.business.data.network.interceptors

import android.util.Log
import com.rwawrzyniak.discofetch.business.data.network.implementation.NetworkConstants.DISCO_OGS_HEADER_RATE_LIMIT
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ResponseInterceptor @Inject constructor() : Interceptor {
	override fun intercept(chain: Interceptor.Chain): Response {
		val response: Response = chain.proceed(chain.request())
		val rateLimitRemaining = response.header(DISCO_OGS_HEADER_RATE_LIMIT)?.toInt() ?: 5

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
