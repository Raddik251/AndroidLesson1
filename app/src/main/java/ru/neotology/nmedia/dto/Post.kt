package ru.neotology.nmedia.dto

import kotlinx.serialization.Serializable

@Serializable
data class Post (
    val id: Long,
    val title: String,
    val content: String,
    val date: String,
    val countLikes: Int,
    val countShares:Int,
    val likes: Int = 0,
    val likedByMe: Boolean = false,
    var videoShowCheck: Boolean = false,
    var link:String? = null
)