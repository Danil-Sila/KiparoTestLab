package ru.silantevdr.kiparotask.data.reposotory

import android.annotation.SuppressLint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.silantevdr.kiparotask.data.network.Common
import ru.silantevdr.kiparotask.domain.model.News
import ru.silantevdr.kiparotask.domain.repository.NewsRepository
import ru.silantevdr.kiparotask.presenter.viewModel.LoadNews
import java.text.SimpleDateFormat

class NewsRepositoryImpl: NewsRepository {
    private var newsList = mutableListOf<News>()
    private val jsonMode = 0
    private val xmlMode = 1

    override fun getNews(loadNews: LoadNews, mode: Int) {
        when(mode) {
            jsonMode -> getNewsJSON(loadNews = loadNews)
            xmlMode -> getNewsXML(loadNews = loadNews)
        }
    }

    override fun newsFilter(keywords: String): List<News> {
        var filterNews = mutableListOf<News>()
        if (keywords.isEmpty()) {
           filterNews = newsList
        } else {
            newsList.forEach { news->
                news.keywords?.forEach {
                    if (it?.contains(keywords) == true) {
                        filterNews.add(news)
                    }
                }
            }
        }
        return filterNews
    }

    private fun getNewsJSON(loadNews: LoadNews) {
        CoroutineScope(Dispatchers.IO).async {
            val response = Common.newsApi(mode = jsonMode).getNewsJSON()
            launch(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val news = sortedNewsByDate(news = response.body()?.news!!)
                    loadNews.onSuccess(news = news)
                    newsList = news as MutableList<News>
                } else {
                    loadNews.onError(response.message())
                }
            }
        }
    }

    private fun getNewsXML(loadNews: LoadNews) {
        CoroutineScope(Dispatchers.IO).async {
            val response = Common.newsApi(mode = xmlMode).getNewsXML()
            launch(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val news = sortedNewsByDate(news = response.body()?.news!!)
                    loadNews.onSuccess(news = news)
                    newsList = news as MutableList<News>
                } else {
                    loadNews.onError(response.message())
                }
            }
        }
    }

    private fun sortedNewsByDate(news: List<News>): List<News> {
        return news.sortedByDescending { getUnixDate(it.date.toString()) }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getUnixDate(date: String): Long? {
        val unix = SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").parse(date)
        return unix?.time
    }

}