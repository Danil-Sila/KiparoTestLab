package ru.silantevdr.kiparotask.presenter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.silantevdr.kiparotask.domain.model.News
import ru.silantevdr.kiparotask.domain.usecase.GetNewsFilterUseCase
import ru.silantevdr.kiparotask.domain.usecase.GetNewsUseCase
import ru.silantevdr.kiparotask.presenter.NewsListFragment

sealed class NewsState {
    class Success(val news: List<News>): NewsState()
    class Error(val message: String): NewsState()
    object Empty: NewsState()
}

interface LoadNews {
    fun onSuccess(news: List<News>)
    fun onError(message: String)
}

class NewsListViewModel(
    private val getNewsUseCase: GetNewsUseCase,
    private val getNewsFilterUseCase: GetNewsFilterUseCase
): ViewModel(), LoadNews {
    private val newsLiveData = MutableLiveData<NewsState>()
    val resultNews: LiveData<NewsState> = newsLiveData


    fun getNews(mode: Int) {
        getNewsUseCase.execute(this, mode)
    }

    fun getNewsFiltered(keywords: String) {
        val filterNews = getNewsFilterUseCase.execute(keywords = keywords)
        newsLiveData.value = NewsState.Success(news = filterNews!!)
    }

    fun getTitleMode(mode: Int): String {
        var title = ""
        when(mode) {
            NewsListFragment.jsonMode -> title = "Режим JSON"
            NewsListFragment.xmlMode -> title = "Режим XML"
        }
        return title
    }

    override fun onSuccess(news: List<News>) {
        if(news.isEmpty()) {
            newsLiveData.value = NewsState.Empty
        } else {
            newsLiveData.value = NewsState.Success(news = news)
        }
    }

    override fun onError(message: String) {
        newsLiveData.value = NewsState.Error(message = message)
    }
}