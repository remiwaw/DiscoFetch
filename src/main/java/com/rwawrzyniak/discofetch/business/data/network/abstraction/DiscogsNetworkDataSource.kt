package com.rwawrzyniak.discofetch.business.data.network.abstraction

import com.rwawrzyniak.discofetch.business.data.network.implementation.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface DiscogsNetworkDataSource{

	// TODO use token for all requests, add it in interceptor
	// https://api.discogs.com/database/search?release_title=nevermind&artist=nirvana&per_page=3&page=1&token=PYRPLaTEXFXOhZHlrwluhzQYytAEiPyKPaWqkjmf

	@GET("/database/search")
	suspend fun searchByAlbum(@Query("release_title") albumName: String,
							@Query("per_page") perPage: Long,
							@Query("page") page: Long,
							@Query("token") token: String = "PYRPLaTEXFXOhZHlrwluhzQYytAEiPyKPaWqkjmf"): SearchResponse

//	@GET("albums/id")
//	suspend fun getAlbum(id: String): List<Album>

}
