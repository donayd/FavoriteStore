package com.dreammkr.favoritestore.domain.use_case

import com.dreammkr.favoritestore.domain.model.Product
import com.dreammkr.favoritestore.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<List<Product>> {
        return repository.getFavoriteProducts()
    }
}
