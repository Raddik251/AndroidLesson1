package ru.neotology.nmedia.viewModel

import androidx.lifecycle.ViewModel
import ru.neotology.nmedia.data.PostRepository
import ru.neotology.nmedia.data.imp.InMemoryPostRepository

class PostViewModel : ViewModel() {

    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    fun onLikeClicked() = repository.like()

    fun onShareClicked() = repository.share()
}