package com.dreammkr.favoritestore.ui.favorites

import app.cash.turbine.test
import com.dreammkr.favoritestore.domain.model.Product
import com.dreammkr.favoritestore.domain.use_case.GetFavoriteProductsUseCase
import com.dreammkr.favoritestore.domain.use_case.ToggleFavoriteUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModelTest {

    private val getFavoriteProductsUseCase: GetFavoriteProductsUseCase = mockk()
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase = mockk()
    private lateinit var viewModel: FavoritesViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState emits Success when use case returns products`() = runTest {
        val products = listOf(
            Product(1, "Product 1", 10.0, "Desc", "Cat", "Img", true)
        )
        every { getFavoriteProductsUseCase() } returns flowOf(products)

        viewModel = FavoritesViewModel(getFavoriteProductsUseCase, toggleFavoriteUseCase)

        viewModel.uiState.test {
            // Check initial state or emission
            val state = awaitItem()
            if (state is FavoritesUiState.Loading) {
                val successState = awaitItem()
                assert(successState is FavoritesUiState.Success)
                assertEquals(products, (successState as FavoritesUiState.Success).products)
            } else {
                assert(state is FavoritesUiState.Success)
                assertEquals(products, (state as FavoritesUiState.Success).products)
            }
        }
    }

    @Test
    fun `uiState emits Empty when use case returns no products`() = runTest {
        every { getFavoriteProductsUseCase() } returns flowOf(emptyList())

        viewModel = FavoritesViewModel(getFavoriteProductsUseCase, toggleFavoriteUseCase)

        viewModel.uiState.test {
            val state = awaitItem()
            if (state is FavoritesUiState.Loading) {
                assert(awaitItem() is FavoritesUiState.Empty)
            } else {
                assert(state is FavoritesUiState.Empty)
            }
        }
    }
}
