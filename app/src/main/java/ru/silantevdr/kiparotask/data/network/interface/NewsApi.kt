package ru.silantevdr.kiparotask.data.network.`interface`

import retrofit2.Response
import retrofit2.http.GET
import ru.silantevdr.kiparotask.domain.model.NewsResponse

interface NewsApi {

    @GET("/static/it_news.json")
    suspend fun getNewsJSON(): Response<NewsResponse>

    @GET("/static/it_news.xml")
    suspend fun getNewsXML(): Response<NewsResponse>
}