package com.ibrahim.sadaPayRepos.di

import com.sadaPayRepos.data.IRemoteDataSource
import com.sadaPayRepos.remote.ReposService
import com.sadaPayRepos.remote.RemoteDataSource
import com.ibrahim.sadaPayRepos.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.Locale
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {
    @Provides
    fun provideRemoteDataSource(
        apiService: ReposService,
    ): IRemoteDataSource = RemoteDataSource(apiService)

    @Provides
    fun provideHttpLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addNetworkInterceptor(loggingInterceptor)
            addInterceptor { chain ->
                val original = chain.request()
                val newUrl = original.url.newBuilder().build()
                chain.proceed(original.newBuilder().url(newUrl).build())
            }
        }.build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
        }
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder().addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(BuildConfig.BASE_URL).client(okHttpClient).build()
    }


    @Provides
    @Singleton
    fun providePostsService(
        retrofit: Retrofit,
    ): ReposService {
        return retrofit.create(ReposService::class.java)
    }


}