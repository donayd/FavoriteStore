package com.dreammkr.favoritestore.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<ProductDto>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): ProductDto

    @GET("users/8")
    suspend fun getUserProfile(): UserDto
}
