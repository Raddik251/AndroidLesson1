package ru.neotology.nmedia.data.imp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.neotology.nmedia.data.PostRepository
import ru.neotology.nmedia.dto.Post

class InMemoryPostRepository : PostRepository {

    private val posts get() = checkNotNull(data.value) {
        "Data value should not be null"
    }

    override val data = MutableLiveData(
        List(10) { index ->
            Post(
                id = index + 1L,
                title = "Неотология - лучший онлайн-университет",
                content = "Университет № ${index + 1} лучших преподавателей",
                date = "16.08.2022",
                countLikes = 999 + (100 * index) + 1,
                countShares = 0
            )
        }
    )

    override fun like(postId: Long) {
        data.value = posts.map {
            if (it.id != postId) it
            else it.copy(
                likedByMe = !it.likedByMe,
                countLikes = if (!it.likedByMe) it.countLikes + 1 else it.countLikes - 1
            )
        }
    }

    override fun share(postId: Long) {
        data.value = posts.map {
            if (it.id != postId) it
            else it.copy(
                countShares = it.countShares + 1
            )
        }
    }

}