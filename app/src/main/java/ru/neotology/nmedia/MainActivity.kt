package ru.neotology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import kotlinx.android.synthetic.main.activity_main.*
import ru.neotology.nmedia.data.imp.PostAdapter
import ru.neotology.nmedia.databinding.ActivityMainBinding
import ru.neotology.nmedia.databinding.PostBinding
import ru.neotology.nmedia.dto.Post
import ru.neotology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostAdapter(viewModel::onLikeClicked, viewModel::onShareClicked)
        binding.postRecyclerView.adapter = adapter
        viewModel.data.observe(this) {posts ->
            adapter.submitList(posts)
        }
    }
}