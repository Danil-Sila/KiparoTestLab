package ru.silantevdr.kiparotask.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import ru.silantevdr.kiparotask.BuildConfig


object RetrofitClient {

    private const val jsonMode = 0
    private const val xmlMode = 1

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client =
        if (BuildConfig.DEBUG) {
            OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor)
                .build()
        } else {
            OkHttpClient().newBuilder()
                .build()
        }

    fun getNews(baseUrl: String, mode: Int): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
        when(mode) {
            jsonMode -> retrofit.addConverterFactory(GsonConverterFactory.create())
            xmlMode -> retrofit.addConverterFactory(SimpleXmlConverterFactory.create())
        }
        return retrofit.build()
    }

}