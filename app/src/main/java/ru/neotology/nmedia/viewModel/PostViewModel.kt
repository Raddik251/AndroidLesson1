package ru.neotology.nmedia.viewModel

import androidx.lifecycle.ViewModel
import ru.neotology.nmedia.data.PostRepository
import ru.neotology.nmedia.data.imp.InMemoryPostRepository
import ru.neotology.nmedia.dto.Post

class PostViewModel : ViewModel() {

    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    fun onLikeClicked(post: Post) = repository.like(post.id)

    fun onShareClicked(post: Post) = repository.share(post.id)
}