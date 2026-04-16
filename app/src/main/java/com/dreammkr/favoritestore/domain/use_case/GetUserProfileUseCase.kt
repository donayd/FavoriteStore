package com.dreammkr.favoritestore.domain.use_case

import com.dreammkr.favoritestore.domain.repository.ProductRepository
import com.dreammkr.favoritestore.domain.repository.User
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): Result<User> {
        return repository.getUserProfile()
    }
}
