package ru.neotology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.*
import androidx.activity.viewModels
import ru.neotology.nmedia.adapter.PostAdapter
import ru.neotology.nmedia.databinding.ActivityMainBinding
import ru.neotology.nmedia.util.hideKeyboard
import ru.neotology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostAdapter(viewModel)
        binding.postRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.saveButton.setOnClickListener {
            with(binding.contentEditText) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)

                clearFocus()
                hideKeyboard()
            }
        }

        viewModel.flag.observe(this) { value ->
            if (value == true) binding.cancelButton.visibility = VISIBLE
            else binding.cancelButton.visibility = INVISIBLE
        }

        binding.cancelButton.setOnClickListener {
            binding.contentEditText.setText(viewModel.currentPost.value?.content)
        }

        viewModel.currentPost.observe(this) { currentPost ->
            binding.contentEditText.setText(currentPost?.content)
        }
    }
}