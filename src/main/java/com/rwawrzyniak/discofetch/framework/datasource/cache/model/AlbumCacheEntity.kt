package com.rwawrzyniak.discofetch.framework.datasource.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class AlbumCacheEntity(

	@PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "cover_image")
	val cover_image: String
)



