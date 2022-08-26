package ru.neotology.nmedia

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import ru.neotology.nmedia.activity.PostContentActivity
import ru.neotology.nmedia.adapter.PostAdapter
import ru.neotology.nmedia.databinding.ActivityMainBinding
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

        binding.fab.setOnClickListener {
            viewModel.onAddClicked()
        }

        viewModel.showVideo.observe(this) { urlVideo ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlVideo.toString())).apply {
                action = Intent.ACTION_VIEW
                type = "photo/video"
            }

            val showVideo =
                Intent.createChooser(intent, getString(R.string.chooserVideoShow))
            startActivity(showVideo)
        }


        viewModel.sharePostContent.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }

            val shareIntent =
                Intent.createChooser(intent, getString(R.string.chooserSharePost))
            startActivity(shareIntent)
        }

        val postContentActivityLauncher = registerForActivityResult(
            PostContentActivity.ResultContract) { postContent ->
            postContent ?: return@registerForActivityResult
            viewModel.onSaveButtonClicked(postContent)
        }

        viewModel.navigateToPostContentScreenEvent.observe(this) {
            postContentActivityLauncher.launch()
        }
    }

}