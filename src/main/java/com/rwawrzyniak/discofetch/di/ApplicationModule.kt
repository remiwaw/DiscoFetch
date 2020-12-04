package com.rwawrzyniak.discofetch.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rwawrzyniak.discofetch.business.data.network.abstraction.DiscogsNetworkDataSource
import com.rwawrzyniak.discofetch.business.data.network.implementation.NetworkConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {

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
