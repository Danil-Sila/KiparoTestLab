package ru.silantevdr.kiparotask.presenter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.silantevdr.kiparotask.adapter.NewsAdapter
import ru.silantevdr.kiparotask.databinding.FragmentNewsListBinding
import ru.silantevdr.kiparotask.presenter.viewModel.NewsListViewModel
import ru.silantevdr.kiparotask.presenter.viewModel.NewsListViewModelFactory
import ru.silantevdr.kiparotask.presenter.viewModel.NewsState

class NewsListFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: NewsAdapter
    private lateinit var vm: NewsListViewModel

    companion object {
        const val jsonMode = 0
        const val xmlMode = 1
        const val mode = "mode"
        fun newInstance(args: Bundle?): NewsListFragment {
            val fragment = NewsListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(this, NewsListViewModelFactory()).get(NewsListViewModel::class.java)
        activity?.title = vm.getTitleMode(arguments?.getInt(mode)!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = NewsAdapter()
        binding.recyclerView.adapter = adapter

        vm.getNews(mode = arguments?.getInt(mode)!!)
        binding.progressBar.visibility = View.VISIBLE

        vm.resultNews.observe(requireActivity()) { state->
            when(state) {
                NewsState.Empty -> {
                    Toast.makeText(context, "Новостей нет!", Toast.LENGTH_SHORT).show()
                }
                is NewsState.Error -> {
                    Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                }
                is NewsState.Success -> {
                    adapter.setNewsList(news = state.news)
                }
            }
            binding.progressBar.visibility = View.GONE
        }

        binding.searchView.setOnQueryTextListener(this)
        return binding.root
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            vm.getNewsFiltered(keywords = query)
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            vm.getNewsFiltered(keywords = newText)
        }
        return false
    }
}