package ru.neotology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import kotlinx.android.synthetic.main.activity_main.*
import ru.neotology.nmedia.databinding.ActivityMainBinding
import ru.neotology.nmedia.dto.Post
import ru.neotology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) {post -> binding.render(post)}

        binding.likeIcon?.setOnClickListener{
            viewModel.onLikeClicked()
        }

        binding.shareIcon?.setOnClickListener{
            viewModel.onShareClicked()
        }

    }

    private fun ActivityMainBinding.render (post: Post) {
        title.text = post.title
        content.text = post.content
        date.text = post.date
        countLikes.text = viewCounts(post.countLikes)
        countShares.text = viewCounts(post.countShares)
        likeIcon?.setImageResource(getLikeIconResId(post.likedByMe))
    }

    @DrawableRes
    private fun getLikeIconResId (liked: Boolean) =
        if(liked) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24

    private fun viewCounts (counts:Int) : String {
        var finalCount = "0"
        when (counts) {
            in 0..999 -> finalCount = counts.toString()
            in 1000..9999 -> finalCount = "${counts / 1000}.${(counts / 1000) / 100}K"
            in 10000..99999 -> finalCount = "${counts / 10000}K"
        }
        return finalCount
    }
}