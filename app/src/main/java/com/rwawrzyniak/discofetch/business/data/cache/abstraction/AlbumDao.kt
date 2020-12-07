package com.rwawrzyniak.discofetch.business.data.cache.abstraction

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rwawrzyniak.discofetch.business.domain.model.AlbumCacheEntity

@Dao
interface AlbumDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertAll(posts: List<AlbumCacheEntity>)

	@Query("SELECT * FROM albums WHERE title = :albumName ORDER BY title ASC")
	fun getAlbums(albumName: String): PagingSource<Int, AlbumCacheEntity>

	@Query("SELECT * FROM albums ORDER BY id DESC")
	fun getAll(): PagingSource<Int, AlbumCacheEntity>

	@Query("DELETE FROM albums")
	fun deleteAlbumsItems(): Int
}
