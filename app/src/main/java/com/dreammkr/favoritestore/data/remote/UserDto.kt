package com.dreammkr.favoritestore.data.remote

data class UserDto(
    val id: Int,
    val username: String,
    val email: String,
    val name: NameDto,
    val avatar: String? = null
)