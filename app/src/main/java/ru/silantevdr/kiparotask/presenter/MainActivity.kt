package ru.silantevdr.kiparotask.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.silantevdr.kiparotask.R
import ru.silantevdr.kiparotask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, NewsModeFragment())
            .commit()
    }
}