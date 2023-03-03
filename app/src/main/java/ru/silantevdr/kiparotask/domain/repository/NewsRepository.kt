package ru.silantevdr.kiparotask.domain.repository

import ru.silantevdr.kiparotask.domain.model.News
import ru.silantevdr.kiparotask.presenter.viewModel.LoadNews

interface NewsRepository {
    fun getNews(loadNews: LoadNews, mode: Int)
    fun newsFilter(keywords: String): List<News>?
}