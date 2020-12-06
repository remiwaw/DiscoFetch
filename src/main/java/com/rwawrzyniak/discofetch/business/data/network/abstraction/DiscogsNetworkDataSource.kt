package com.rwawrzyniak.discofetch.business.data.network.abstraction

import com.rwawrzyniak.discofetch.business.data.network.implementation.response.GetReleaseByIdResponse
import com.rwawrzyniak.discofetch.business.data.network.implementation.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface DiscogsNetworkDataSource{

	// TODO use token for all requests, add it in interceptor
	// https://api.discogs.com/database/search?release_title=nevermind&artist=nirvana&per_page=3&page=1&token=PYRPLaTEXFXOhZHlrwluhzQYytAEiPyKPaWqkjmf

	@GET("/database/search")
	suspend fun searchByAlbumTitle(@Query("release_title") albumName: String = "",
								   @Query("type") type: String = "release",
								   @Query("per_page") perPage: Int,
								   @Query("page") page: Int,
								   @Query("token") token: String = "PYRPLaTEXFXOhZHlrwluhzQYytAEiPyKPaWqkjmf"): SearchResponse


//	https://api.discogs.com/releases/2028757
	@GET("/releases/{releaseId}")
	suspend fun getReleaseById(@Path("releaseId") releaseId: Long): GetReleaseByIdResponse

}
