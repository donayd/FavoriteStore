package com.dreammkr.favoritestore.domain.repository

import com.dreammkr.favoritestore.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<Result<List<Product>>>
    fun getFavoriteProducts(): Flow<List<Product>>
    suspend fun toggleFavorite(product: Product): Result<Unit>
    suspend fun getProductById(id: Int): Result<Product>
    suspend fun getUserProfile(): Result<User>
}

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val name: String,
    val avatar: String
)
