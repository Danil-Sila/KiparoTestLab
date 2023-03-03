package ru.silantevdr.kiparotask.domain.usecase

import ru.silantevdr.kiparotask.domain.model.News
import ru.silantevdr.kiparotask.domain.repository.NewsRepository

class GetNewsFilterUseCase(private val newsRepository: NewsRepository) {
    fun execute(keywords: String): List<News>? {
        return newsRepository.newsFilter(keywords)
    }
}