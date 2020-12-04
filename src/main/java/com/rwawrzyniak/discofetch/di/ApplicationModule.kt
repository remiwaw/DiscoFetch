package com.rwawrzyniak.discofetch.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rwawrzyniak.discofetch.business.data.cache.abstraction.CacheDb
import com.rwawrzyniak.discofetch.business.data.cache.abstraction.CacheDb.Companion.DB_NAME
import com.rwawrzyniak.discofetch.business.data.network.abstraction.DiscogsNetworkDataSource
import com.rwawrzyniak.discofetch.business.data.network.implementation.NetworkConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {

	@Singleton
	@Provides
	fun provideYourDatabase(
		@ApplicationContext app: Context
	) = Room.databaseBuilder(
		app,
		CacheDb::class.java,
		DB_NAME
	).build()

	@Singleton
	@Provides
	fun provideYourDao(db: CacheDb) = db.getAlbumDao()

	@JvmStatic
	@Singleton
	@Provides
	fun provideRetrofitBuilder(gsonBuilder: Gson): Retrofit.Builder{
		return Retrofit.Builder()
			.baseUrl(NetworkConstants.BASE_URL)
			.addConverterFactory(GsonConverterFactory.create(gsonBuilder))
	}

	@JvmStatic
	@Singleton
	@Provides
	fun provideGsonBuilder(): Gson {
		return GsonBuilder()
			.excludeFieldsWithoutExposeAnnotation()
			.create()
	}

	@Singleton
	@Provides
	fun provideDiscogsNetworkDataSource(retrofitBuilder: Retrofit.Builder, httpClient: OkHttpClient): DiscogsNetworkDataSource {
		return retrofitBuilder
			.client(httpClient)
			.build()
			.create(DiscogsNetworkDataSource::class.java)
	}

	@Singleton
	@Provides
	fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
		return OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build()
	}

	@Singleton
	@Provides
	fun provideHttpLoginInterceptor() =
		HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }

}
