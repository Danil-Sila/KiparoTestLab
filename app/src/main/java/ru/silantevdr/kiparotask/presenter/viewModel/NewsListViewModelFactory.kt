package ru.silantevdr.kiparotask.presenter.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.silantevdr.kiparotask.data.reposotory.NewsRepositoryImpl
import ru.silantevdr.kiparotask.domain.usecase.GetNewsFilterUseCase
import ru.silantevdr.kiparotask.domain.usecase.GetNewsUseCase

class NewsListViewModelFactory: ViewModelProvider.Factory {

    private val newsRepository by lazy(LazyThreadSafetyMode.NONE) {
        NewsRepositoryImpl()
    }

    private val getNewsUseCase by lazy ( LazyThreadSafetyMode.NONE ) {
        GetNewsUseCase(newsRepository = newsRepository)
    }

    private val getNewsFilterUseCase by lazy ( LazyThreadSafetyMode.NONE ) {
        GetNewsFilterUseCase(newsRepository = newsRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsListViewModel(
            getNewsUseCase = getNewsUseCase,
            getNewsFilterUseCase = getNewsFilterUseCase
        ) as T
    }

}