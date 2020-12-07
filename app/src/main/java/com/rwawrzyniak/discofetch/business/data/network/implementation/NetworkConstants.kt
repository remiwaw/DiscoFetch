package com.rwawrzyniak.discofetch.business.data.network.implementation

object NetworkConstants {
	const val BASE_URL = "https://api.discogs.com/"
	const val PAGE_SIZE = 20
	// TODO token should not be here, in the best scenario it should be fetched from backend server and then saved in safeSharedPreferences
	// also it shouldnt be version in GIT, like it is now.

	val TOKEN = "token" to "PYRPLaTEXFXOhZHlrwluhzQYytAEiPyKPaWqkjmf"
	val DISCO_OGS_HEADER_RATE_LIMIT = "x-discogs-ratelimit-remaining"
}
