package com.rwawrzyniak.discofetch.framework.datasource.cache.database
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rwawrzyniak.discofetch.framework.datasource.cache.model.AlbumCacheEntity

@Dao
interface AlbumDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertAll(posts: List<AlbumCacheEntity>)

	@Query("SELECT * FROM albums WHERE title = :albumName ORDER BY title ASC")
	fun getAlbums(albumName: String): PagingSource<Int, AlbumCacheEntity>

	@Query("DELETE FROM albums")
	fun deleteAlbumsItems(): Int
}
