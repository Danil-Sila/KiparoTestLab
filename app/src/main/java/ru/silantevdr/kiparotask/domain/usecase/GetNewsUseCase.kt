package ru.silantevdr.kiparotask.domain.usecase

import ru.silantevdr.kiparotask.domain.repository.NewsRepository
import ru.silantevdr.kiparotask.presenter.viewModel.LoadNews

class GetNewsUseCase(private val newsRepository: NewsRepository) {
     fun execute(loadNews: LoadNews, mode: Int) {
         newsRepository.getNews(loadNews = loadNews, mode = mode)
    }
}