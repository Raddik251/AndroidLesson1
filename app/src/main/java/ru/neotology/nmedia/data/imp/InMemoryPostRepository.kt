package ru.neotology.nmedia.data.imp

import androidx.lifecycle.MutableLiveData
import ru.neotology.nmedia.data.PostRepository
import ru.neotology.nmedia.dto.Post

class InMemoryPostRepository : PostRepository {

    private var nextId = GENERATED_POSTS_AMOUNT.toLong()

    private val posts get() = checkNotNull(data.value) {
        "Data value should not be null"
    }

    override val data = MutableLiveData(
        List(GENERATED_POSTS_AMOUNT) { index ->
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

    override fun remove(postId: Long) {
        data.value = posts.filter {it.id != postId}
    }

    override fun save(post: Post) {

        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update (post)
    }

    private fun insert(post: Post) {

        data.value = listOf(
            post.copy(
                id = ++nextId
            )
        ) + posts
    }

    private fun update(post: Post) {

        data.value = posts.map {
            if (it.id == post.id) post else it
        }
    }

    companion object {
        private const val GENERATED_POSTS_AMOUNT = 100
    }

}