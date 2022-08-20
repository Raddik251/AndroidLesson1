package ru.neotology.nmedia.adapter

import ru.neotology.nmedia.dto.Post

interface PostInteractionListener {

    fun onLikeClicked (post: Post)
    fun onShareClicked (post: Post)
    fun onRemoveClicked (post: Post)
    fun onEditClicked (post: Post)
}