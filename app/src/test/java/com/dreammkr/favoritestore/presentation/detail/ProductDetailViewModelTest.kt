package com.dreammkr.favoritestore.presentation.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.dreammkr.favoritestore.domain.model.Product
import com.dreammkr.favoritestore.domain.use_case.GetProductByIdUseCase
import com.dreammkr.favoritestore.domain.use_case.ToggleFavoriteUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailViewModelTest {

    private val getProductByIdUseCase: GetProductByIdUseCase = mockk()
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()
    private val productId = 1

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadProduct sets Success state when use case returns product`() = runTest {
        val product = Product(productId, "Product 1", 10.0, "Desc", "Cat", "Img", false)
        coEvery { getProductByIdUseCase(productId) } returns Result.success(product)

        val viewModel = ProductDetailViewModel(
            getProductByIdUseCase,
            toggleFavoriteUseCase,
            SavedStateHandle(mapOf("productId" to productId))
        )

        viewModel.state.test {
            assert(awaitItem() is ProductDetailState.Loading)
            val successState = awaitItem()
            assert(successState is ProductDetailState.Success)
            assertEquals(product, (successState as ProductDetailState.Success).product)
        }
    }

    @Test
    fun `loadProduct sets Error state when use case returns failure`() = runTest {
        val errorMessage = "Not Found"
        coEvery { getProductByIdUseCase(productId) } returns Result.failure(Exception(errorMessage))

        val viewModel = ProductDetailViewModel(
            getProductByIdUseCase,
            toggleFavoriteUseCase,
            SavedStateHandle(mapOf("productId" to productId))
        )

        viewModel.state.test {
            assert(awaitItem() is ProductDetailState.Loading)
            val errorState = awaitItem()
            assert(errorState is ProductDetailState.Error)
            assertEquals(errorMessage, (errorState as ProductDetailState.Error).message)
        }
    }

    @Test
    fun `toggleFavorite updates state locally when successful`() = runTest {
        val product = Product(productId, "Product 1", 10.0, "Desc", "Cat", "Img", false)
        coEvery { getProductByIdUseCase(productId) } returns Result.success(product)
        coEvery { toggleFavoriteUseCase(product) } returns Result.success(Unit)

        val viewModel = ProductDetailViewModel(
            getProductByIdUseCase,
            toggleFavoriteUseCase,
            SavedStateHandle(mapOf("productId" to productId))
        )

        viewModel.state.test {
            awaitItem() // Loading
            awaitItem() // Success Initial
            
            viewModel.toggleFavorite()
            
            val updatedState = awaitItem()
            assert(updatedState is ProductDetailState.Success)
            assertEquals(true, (updatedState as ProductDetailState.Success).product.isFavorite)
        }
    }
}
