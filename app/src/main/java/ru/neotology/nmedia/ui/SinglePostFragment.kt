package ru.neotology.nmedia.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.neotology.nmedia.R
import ru.neotology.nmedia.adapter.PostAdapter
import ru.neotology.nmedia.databinding.PostSingleBinding
import ru.neotology.nmedia.dto.Post
import ru.neotology.nmedia.viewModel.PostViewModel

class SinglePostFragment : Fragment() {

    private val viewModel by viewModels<PostViewModel>()

    private val args by navArgs<SinglePostFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.showVideo.observe(this) { urlVideo ->
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(urlVideo)
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

        viewModel.navigateToPostContentScreenEvent.observe(this) { initialContent ->
            val direction = SinglePostFragmentDirections.toPostContentFragment(initialContent)
            findNavController().navigate(direction)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = PostSingleBinding.inflate(layoutInflater, container, false).also { binding ->

        val post = Post (
            id = args.id,
            title = args.title,
            date = args.date,
            content = args.content,
            countLikes = args.countLikes,
            countShares = args.countShares,
            likes = args.likes,
            likedByMe = args.likedByMe,
            videoShowCheck = args.videoShowCheck,
            link = args.link
        )

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == post.id } ?: return@observe

            with(binding) {
                title.text = args.title
                date.text = args.date
                content.text = args.content
                likeIcon.text = args.countLikes.toString()
                shareIcon.text = args.countShares.toString()
                videoImage.visibility = if (args.videoShowCheck) View.GONE else View.VISIBLE
                launchVideo.visibility = if (args.videoShowCheck) View.GONE else View.VISIBLE

                likeIcon.setOnClickListener { viewModel.onLikeClicked(post) }
                shareIcon.setOnClickListener { viewModel.onShareClicked(post) }
                launchVideo.setOnClickListener { viewModel.onVideoShow(post) }
                videoImage.setOnClickListener { viewModel.onVideoShow(post) }
                options.setOnClickListener{ }
            }
        }
    }.root

}