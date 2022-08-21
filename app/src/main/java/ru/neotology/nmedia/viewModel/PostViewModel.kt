package ru.neotology.nmedia.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.neotology.nmedia.adapter.PostAdapter
import ru.neotology.nmedia.adapter.PostInteractionListener
import ru.neotology.nmedia.data.PostRepository
import ru.neotology.nmedia.data.imp.InMemoryPostRepository
import ru.neotology.nmedia.dto.Post

class PostViewModel : ViewModel(), PostInteractionListener {

    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    var flag = MutableLiveData<Boolean?>(false)

    val currentPost = MutableLiveData<Post?>(null)

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
        this.flag.value = !flag.value!!
    }

    //region PostInreractionListener

    override fun onLikeClicked(post: Post) = repository.like(post.id)

    override fun onShareClicked(post: Post) = repository.share(post.id)

    override fun onRemoveClicked(post: Post) = repository.remove(post.id)

    override fun onEditClicked(post: Post) {
        currentPost.value = post
        this.flag.value = !flag.value!!
    }

    //endregion PostInreractionListener
}