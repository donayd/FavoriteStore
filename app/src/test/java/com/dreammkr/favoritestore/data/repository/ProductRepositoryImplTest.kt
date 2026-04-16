package com.dreammkr.favoritestore.data.repository

import com.dreammkr.favoritestore.data.local.ProductDao
import com.dreammkr.favoritestore.data.remote.ApiService
import com.dreammkr.favoritestore.data.remote.NameDto
import com.dreammkr.favoritestore.data.remote.ProductDto
import com.dreammkr.favoritestore.data.remote.UserDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ProductRepositoryImplTest {

    private val apiService: ApiService = mockk()
    private val productDao: ProductDao = mockk()
    private lateinit var repository: ProductRepositoryImpl

    @Before
    fun setup() {
        repository = ProductRepositoryImpl(apiService, productDao)
    }

    @Test
    fun `getProducts returns products from API and maps favorite status correctly`() = runTest {
        val remoteProducts = listOf(
            ProductDto(1, "Title", 10.0, "Desc", "Cat", "Img")
        )
        coEvery { apiService.getProducts() } returns remoteProducts
        coEvery { productDao.isFavorite(1) } returns true

        val result = repository.getProducts()

        assertTrue(result.isSuccess)
        val products = result.getOrNull()
        assertEquals(1, products?.size)
        assertTrue(products!![0].isFavorite)
    }

    @Test
    fun `getUserProfile maps remote user to domain user correctly`() = runTest {
        val userDto = UserDto(8, "johndoe", "john@gmail.com", NameDto("John", "Doe"), null)
        coEvery { apiService.getUserProfile() } returns userDto

        val result = repository.getUserProfile()

        assertTrue(result.isSuccess)
        val user = result.getOrNull()
        assertEquals("John Doe", user?.name)
        assertEquals("https://i.pravatar.cc/150?u=8", user?.avatar)
    }
}
