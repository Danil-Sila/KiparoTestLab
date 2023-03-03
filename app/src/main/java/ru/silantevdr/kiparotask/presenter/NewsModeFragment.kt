package ru.silantevdr.kiparotask.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.silantevdr.kiparotask.R
import ru.silantevdr.kiparotask.databinding.FragmentNewsModeBinding


class NewsModeFragment : Fragment() {

    private var _binding: FragmentNewsModeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsModeBinding.inflate(inflater, container, false)
        binding.jsonButton.setOnClickListener {
            openListNews(mode = 0)
        }

        binding.xmlButton.setOnClickListener {
            openListNews(mode = 1)
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        activity?.title = "Kiparo тестовое задание"
    }

    private fun openListNews(mode: Int) {
        if (internetIsConnected()) {
            val bundle = Bundle()
            bundle.putInt("mode", mode)
            val newsListFragment = NewsListFragment.newInstance(args = bundle)
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.content, newsListFragment)
                ?.addToBackStack(null)
                ?.commit()
        } else {
            Toast.makeText(context,"Отсутствует подключение к интернету", Toast.LENGTH_SHORT).show()
        }
    }

    private fun internetIsConnected(): Boolean {
        return try {
            val command = "ping -c 1 google.com"
            Runtime.getRuntime().exec(command).waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }

}