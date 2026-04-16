package com.dreammkr.favoritestore.data.repository

import com.dreammkr.favoritestore.data.local.ProductDao
import com.dreammkr.favoritestore.data.local.ProductEntity
import com.dreammkr.favoritestore.data.remote.ApiService
import com.dreammkr.favoritestore.domain.model.Product
import com.dreammkr.favoritestore.domain.repository.ProductRepository
import com.dreammkr.favoritestore.domain.repository.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val productDao: ProductDao
) : ProductRepository {

    override fun getProducts(): Flow<Result<List<Product>>> = flow {
        try {
            val remoteProducts = apiService.getProducts()
            emit(Result.success(remoteProducts))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.combine(productDao.getFavoriteIds()) { result, favoriteIds ->
        val favoriteSet = favoriteIds.toSet()
        result.map { dtos ->
            dtos.map { dto ->
                Product(
                    id = dto.id,
                    title = dto.title,
                    price = dto.price,
                    description = dto.description,
                    category = dto.category,
                    image = dto.image,
                    isFavorite = favoriteSet.contains(dto.id)
                )
            }
        }
    }

    override fun getFavoriteProducts(): Flow<List<Product>> {
        return productDao.getFavoriteProducts().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun toggleFavorite(product: Product): Result<Unit> {
        return try {
            if (product.isFavorite) {
                productDao.deleteProduct(product.toEntity())
            } else {
                productDao.insertProduct(product.toEntity().copy(isFavorite = true))
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProductById(id: Int): Result<Product> {
        return try {
            val dto = apiService.getProduct(id)
            val isFav = productDao.isFavorite(id)
            Result.success(
                Product(
                    id = dto.id,
                    title = dto.title,
                    price = dto.price,
                    description = dto.description,
                    category = dto.category,
                    image = dto.image,
                    isFavorite = isFav
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserProfile(): Result<User> {
        return try {
            val dto = apiService.getUserProfile()
            Result.success(
                User(
                    id = dto.id,
                    username = dto.username,
                    email = dto.email,
                    name = "${dto.name.firstname} ${dto.name.lastname}",
                    avatar = "https://i.pravatar.cc/150?u=${dto.id}"
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

fun ProductEntity.toDomain() = Product(id, title, price, description, category, image, isFavorite)
fun Product.toEntity() = ProductEntity(id, title, price, description, category, image, isFavorite)
