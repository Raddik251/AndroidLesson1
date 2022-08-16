package ru.neotology.nmedia.data.imp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.neotology.nmedia.data.PostRepository
import ru.neotology.nmedia.dto.Post

class InMemoryPostRepository : PostRepository {

    override val data = MutableLiveData(
        Post(
            id = 1L,
            title = "Неотология - лучший онлайн-университет",
            content = "Лучших преподавателей",
            date = "16.08.2022",
            countLikes = 999,
            countShares = 0
        )
    )

    override fun like() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }

        val likedPost = currentPost.copy(
            countLikes = if (!currentPost.likedByMe) currentPost.countLikes + 1 else currentPost.countLikes - 1,
            likedByMe = !currentPost.likedByMe
        )

        data.value = likedPost
    }

    override fun share() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }

        val sharedPost = currentPost.copy(
            countShares = currentPost.countShares + 1
        )

        data.value = sharedPost
    }

}