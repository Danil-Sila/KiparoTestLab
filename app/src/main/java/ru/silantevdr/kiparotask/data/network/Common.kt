package ru.silantevdr.kiparotask.data.network

import ru.silantevdr.kiparotask.data.network.`interface`.NewsApi

object Common {

    private val BASE_URL = "https://api2.kiparo.com"

    fun newsApi(mode: Int): NewsApi = RetrofitClient.getNews(BASE_URL, mode).create(NewsApi::class.java)
}