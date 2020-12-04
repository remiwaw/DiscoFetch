package com.rwawrzyniak.discofetch.business.data.cache.abstraction

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rwawrzyniak.discofetch.framework.datasource.cache.database.AlbumDao
import com.rwawrzyniak.discofetch.framework.datasource.cache.database.NewsRemoteKeyDao
import com.rwawrzyniak.discofetch.framework.datasource.cache.model.AlbumCacheEntity
import com.rwawrzyniak.discofetch.framework.datasource.cache.model.AlbumRemoteKeyEntity

@Database(
	entities = [AlbumCacheEntity::class, AlbumRemoteKeyEntity::class],
	version = 1
)
abstract class CacheDb : RoomDatabase() {
	abstract fun getAlbumDao(): AlbumDao
	abstract fun getAlbumRemoteKeysDao(): NewsRemoteKeyDao

	companion object {
		const val DB_NAME = "cacheDb"
	}
}


