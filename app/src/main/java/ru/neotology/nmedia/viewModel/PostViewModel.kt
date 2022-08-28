package ru.neotology.nmedia.viewModel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.neotology.nmedia.adapter.PostInteractionListener
import ru.neotology.nmedia.data.PostRepository
import ru.neotology.nmedia.data.imp.FilePostRepository
import ru.neotology.nmedia.dto.Post
import ru.neotology.nmedia.util.SingleLiveEvent

class PostViewModel(
    application: Application
) : AndroidViewModel(application),
    PostInteractionListener {

    private val repository: PostRepository =
        FilePostRepository(application)

    val data by repository::data

    val sharePostContent = SingleLiveEvent<String>()
    val showVideo = SingleLiveEvent<String?>()
    val navigateToPostContentScreenEvent = SingleLiveEvent<String>()
    val navigateToPostSingleScreenEvent = SingleLiveEvent<Post>()

    val currentPost = MutableLiveData<Post?>(null)

    fun onSaveButtonClicked(content: String) {
        if (content.isBlank()) return

        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
                id = PostRepository.NEW_POST_ID,
                title = "Новый Пост",
                content = content,
                date = "20.08.2022",
                countLikes = 0,
                countShares = 0
            )
        repository.save(post)
        currentPost.value = null
    }

    //region PostInreractionListener

    fun onAddClicked() {
        navigateToPostContentScreenEvent.call()
    }

    override fun onLikeClicked(post: Post) = repository.like(post.id)

    override fun onShareClicked(post: Post) {
        repository.share(post.id)
        sharePostContent.value = post.content
    }

    override fun onRemoveClicked(post: Post) = repository.remove(post.id)

    override fun onEditClicked(post: Post) {
        currentPost.value = post
        navigateToPostContentScreenEvent.value = post.content
    }

    override fun onVideoShow(post: Post) {
        post.link = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
        showVideo.value = post.link
    }

    override fun onSinglePostShow(post: Post) {
        currentPost.value = post
        navigateToPostSingleScreenEvent.value = post
    }
    //endregion PostInreractionListener
}