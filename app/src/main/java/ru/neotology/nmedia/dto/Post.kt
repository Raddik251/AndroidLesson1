package ru.neotology.nmedia.dto

import kotlinx.serialization.Serializable

@Serializable
data class Post (
    val id: Long,
    val title: String,
    val content: String,
    val date: String,
    var countLikes: Int,
    var countShares:Int,
    val likes: Int = 0,
    var likedByMe: Boolean = false,
    var videoShowCheck: Boolean = false,
    var link:String? = null
)