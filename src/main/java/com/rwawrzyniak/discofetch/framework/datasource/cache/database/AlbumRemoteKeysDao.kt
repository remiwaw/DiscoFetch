package com.rwawrzyniak.discofetch.framework.datasource.cache.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rwawrzyniak.discofetch.framework.datasource.cache.model.AlbumRemoteKeyEntity

@Dao
interface NewsRemoteKeyDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertAll(remoteKey: List<AlbumRemoteKeyEntity>)
	@Query("SELECT * FROM AlbumRemoteKeyEntity WHERE albumId = :albumId")
	fun remoteKeysByNewsId(albumId: Long): AlbumRemoteKeyEntity?
	@Query("DELETE FROM AlbumRemoteKeyEntity")
	fun clearRemoteKeys()
}
