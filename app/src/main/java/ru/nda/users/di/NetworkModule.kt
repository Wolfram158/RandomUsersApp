package ru.nda.users.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.nda.users.data.network.ApiService

@Module
class NetworkModule {
    @AppScope
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().client(
            OkHttpClient.Builder().addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
                .build())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @AppScope
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create<ApiService>()
    }

    companion object {
        private const val BASE_URL = "https://randomuser.me/"
    }
}