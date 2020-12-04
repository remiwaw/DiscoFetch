package com.rwawrzyniak.discofetch.business.data

import com.rwawrzyniak.discofetch.business.data.network.abstraction.DiscogsNetworkDataSource
import javax.inject.Inject


// add db
class DiscogsSearchRepository @Inject constructor(val discogsNetworkDataSource: DiscogsNetworkDataSource)  {
	suspend fun searchByAlbumName(albumName: String){
		discogsNetworkDataSource.searchByAlbum(albumName, 1, 3)
	}
}
