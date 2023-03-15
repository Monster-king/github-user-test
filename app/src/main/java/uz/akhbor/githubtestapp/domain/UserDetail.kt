package uz.akhbor.githubtestapp.domain

import java.time.LocalDateTime

data class UserDetail(
    val login: String,
    val avatarUrl: String,
    val email: String,
    val organizationsUrl: String,
    val followers: Int,
    val following: Int,
    val createdAt: LocalDateTime?
)