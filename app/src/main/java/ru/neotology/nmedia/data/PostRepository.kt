package ru.neotology.nmedia.data

import androidx.lifecycle.LiveData
import ru.neotology.nmedia.dto.Post

interface PostRepository {

    val data: LiveData<Post>

    fun like()

    fun share()
}