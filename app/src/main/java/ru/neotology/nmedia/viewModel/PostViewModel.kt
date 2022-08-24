package ru.neotology.nmedia.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.neotology.nmedia.activity.PostContentActivity
import ru.neotology.nmedia.adapter.PostInteractionListener
import ru.neotology.nmedia.data.PostRepository
import ru.neotology.nmedia.data.imp.InMemoryPostRepository
import ru.neotology.nmedia.dto.Post
import ru.neotology.nmedia.util.SingleLiveEvent

class PostViewModel : ViewModel(), PostInteractionListener {

    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    val sharePostContent = SingleLiveEvent<String> ()
    val navigateToPostContentScreenEvent = SingleLiveEvent<Unit> ()

    private var currentPost = MutableLiveData<Post?> (null)

    fun onSaveButtonClicked(content: String) {
        if (content.isBlank()) return

        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            title = "Новый Пост",
            content = "Текст нового поста",
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
        PostRepository.editText = post.content
        currentPost.value = post
        navigateToPostContentScreenEvent.call()
    }
    //endregion PostInreractionListener
}