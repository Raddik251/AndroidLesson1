package ru.neotology.nmedia.dto

data class Post (
    val id: Long,
    val title: String,
    val content: String,
    val date: String,
    val countLikes: Int,
    val countShares:Int,
    val likes: Int = 0,
    val likedByMe: Boolean = false
)