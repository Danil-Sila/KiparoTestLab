package ru.silantevdr.kiparotask.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.silantevdr.kiparotask.databinding.ViewNewsBinding
import ru.silantevdr.kiparotask.domain.model.News
import java.text.SimpleDateFormat

class NewsAdapter(): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var newsList = listOf<News>()
    private val months = arrayListOf(
        "Января", "Февраля", "Марта", "Апреля", "Мая", "Июня",
        "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря"
    )

    class NewsViewHolder(val binding: ViewNewsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ViewNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = newsList[position]
        with(holder) {
            binding.title.text = newsItem.title
            binding.date.text = getDateFormat(newsItem.date ?: "")
            binding.description.text = newsItem.description
        }
    }

    override fun getItemCount(): Int = newsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setNewsList(news: List<News>) {
        newsList = news
        notifyDataSetChanged()
    }
    @SuppressLint("SimpleDateFormat")
    private fun getDateFormat(date: String):String {
        val dateString = SimpleDateFormat("dd MM yyyy").format(getUnixDate(date)).split(" ")
        val day = dateString[0]
        val month = months[dateString[1].toInt()]
        val year = dateString[2]
        return "$day $month $year г."
    }

    @SuppressLint("SimpleDateFormat")
    private fun getUnixDate(date: String): Long? {
        val unix = SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").parse(date)
        return unix?.time
    }

}