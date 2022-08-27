package ru.neotology.nmedia.data.imp

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.neotology.nmedia.data.PostRepository
import ru.neotology.nmedia.dto.Post
import kotlin.properties.Delegates

class SharedPrefsPostRepository (
    application: Application
        ) : PostRepository {

    private val prefs = application.getSharedPreferences(
        "repo", Context.MODE_PRIVATE
    )

    private var nextId: Long by Delegates.observable(
        prefs.getLong(NEXT_ID_PREFS_KEY, 0L)
    ) { _, _, newValue ->
        prefs.edit { putLong(NEXT_ID_PREFS_KEY, newValue) }
    }

    private var posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }

    set(value) {
        prefs.edit {
            val serializedPosts = Json.encodeToString(value)
            putString(POSTS_PREFS_KEY, serializedPosts)
        }
        data.value = value
    }

    override val data: MutableLiveData<List<Post>>

    init {
        val serializedPosts = prefs.getString(POSTS_PREFS_KEY, null)
        val posts:List<Post> = if (serializedPosts != null) {
             Json.decodeFromString(serializedPosts)
        } else emptyList()
        data = MutableLiveData(posts)
    }

    override fun like(postId: Long) {
        posts = posts.map {
            if (it.id != postId) it
            else it.copy(
                likedByMe = !it.likedByMe,
                countLikes = if (!it.likedByMe) it.countLikes + 1 else it.countLikes - 1
            )
        }
    }

    override fun share(postId: Long) {
        posts = posts.map {
            if (it.id != postId) it
            else it.copy(
                countShares = it.countShares + 1
            )
        }
    }

    override fun remove(postId: Long) {
        posts = posts.filter { it.id != postId }
    }

    override fun save(post: Post) {

        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    private fun insert(post: Post) {

        posts = listOf(
            post.copy(
                id = ++nextId
            )
        ) + posts
    }

    private fun update(post: Post) {

        posts = posts.map {
            if (it.id == post.id) post else it
        }
    }

    companion object {
        private const val POSTS_PREFS_KEY = "posts"
        private const val NEXT_ID_PREFS_KEY = "nextId"
    }

}