package ru.neotology.nmedia.data

import androidx.lifecycle.LiveData
import ru.neotology.nmedia.dto.Post

interface PostRepository {

    val data: LiveData<List<Post>>

    fun like(postId: Long)

    fun share(postId: Long)
}