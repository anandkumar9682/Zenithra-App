/**
 * Author: Anand Kumar
 * Email: anandkumar.docs@gmail.com
 * Date: 2025-04-13
 * Description: This module provides dependencies related to networking including interceptors, OkHttp client, and Retrofit setup for API communication.
 *              The network interceptor checks for internet availability, and the header interceptor adds necessary headers for API requests.
 */

package com.asuni.zenithra.di

import android.content.Context
import com.asuni.zenithra.BuildConfig
import com.asuni.zenithra.network.NetworkApi
import com.asuni.zenithra.util.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

// Custom Interceptor to check for internet connectivity
class NetworkInterceptor @Inject constructor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!NetworkUtils.isInternetAvailable(context)) {
            throw NoInternetException("No Internet Connection")
        }
        return chain.proceed(chain.request())
    }
}

// Custom Exception for no internet connection
class NoInternetException(message: String) : IOException(message)

// Header Interceptor to add necessary headers to requests
class HeaderInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder().apply {
            addHeader("X-Rapidapi-Host", BuildConfig.X_Rapidapi_Host)
            addHeader("X-Rapidapi-Key", BuildConfig.X_Rapidapi_Key)
        }
        return chain.proceed(requestBuilder.build())
    }
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // Provide HeaderInterceptor using Hilt's @Inject constructor
    @Provides
    @Singleton
    fun provideHeaderInterceptor(): HeaderInterceptor = HeaderInterceptor()

    // Provide NetworkInterceptor that checks internet availability
    @Provides
    @Singleton
    fun provideNetworkInterceptor(@ApplicationContext context: Context): NetworkInterceptor =
        NetworkInterceptor(context)

    // Provide OkHttpClient with interceptors
    @Provides
    @Singleton
    fun provideOkHttpClient(
        headerInterceptor: HeaderInterceptor,
        networkInterceptor: NetworkInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder().apply {
            addInterceptor(headerInterceptor)
            addInterceptor(networkInterceptor)
            if (BuildConfig.IS_STAGING || BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                addInterceptor(loggingInterceptor)
            }
        }
        return builder.build()
    }

    // Provide Retrofit instance
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()

    // Provide API service for UsersApi
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): NetworkApi =
        retrofit.create(NetworkApi::class.java)
}