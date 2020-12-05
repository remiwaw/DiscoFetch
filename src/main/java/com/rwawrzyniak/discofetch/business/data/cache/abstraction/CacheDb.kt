package com.rwawrzyniak.discofetch.business.data.cache.abstraction

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rwawrzyniak.discofetch.business.domain.model.AlbumCacheEntity

@Database(
	entities = [AlbumCacheEntity::class],
	version = 1
)
abstract class CacheDb : RoomDatabase() {
	abstract fun getAlbumDao(): AlbumDao

	companion object {
		const val DB_NAME = "cacheDb"
	}
}


