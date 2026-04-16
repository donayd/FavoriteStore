package com.dreammkr.favoritestore.presentation.products

import app.cash.turbine.test
import com.dreammkr.favoritestore.domain.model.Product
import com.dreammkr.favoritestore.domain.use_case.GetProductsUseCase
import com.dreammkr.favoritestore.domain.use_case.ToggleFavoriteUseCase
import io.mockk.coEvery
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
class ProductListViewModelTest {

    private val getProductsUseCase: GetProductsUseCase = mockk()
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase = mockk()
    private lateinit var viewModel: ProductListViewModel
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
    fun `loadProducts sets Success state when use case returns products`() = runTest {
        val products = listOf(
            Product(1, "Product 1", 10.0, "Desc", "Cat", "Img", false)
        )
        every { getProductsUseCase() } returns flowOf(Result.success(products))

        viewModel = ProductListViewModel(getProductsUseCase, toggleFavoriteUseCase)

        viewModel.state.test {
            // Initial loading state
            assert(awaitItem() is ProductListState.Loading)
            
            val successState = awaitItem()
            assert(successState is ProductListState.Success)
            assertEquals(products, (successState as ProductListState.Success).products)
        }
    }

    @Test
    fun `loadProducts sets Error state when use case returns failure`() = runTest {
        val errorMessage = "Network Error"
        every { getProductsUseCase() } returns flowOf(Result.failure(Exception(errorMessage)))

        viewModel = ProductListViewModel(getProductsUseCase, toggleFavoriteUseCase)

        viewModel.state.test {
            assert(awaitItem() is ProductListState.Loading)
            val errorState = awaitItem()
            assert(errorState is ProductListState.Error)
            assertEquals(errorMessage, (errorState as ProductListState.Error).message)
        }
    }

    @Test
    fun `toggleFavorite sends ShowError event when use case fails`() = runTest {
        val product = Product(1, "Product 1", 10.0, "Desc", "Cat", "Img", false)
        every { getProductsUseCase() } returns flowOf(Result.success(emptyList()))
        coEvery { toggleFavoriteUseCase(product) } returns Result.failure(Exception("DB Error"))

        viewModel = ProductListViewModel(getProductsUseCase, toggleFavoriteUseCase)

        viewModel.events.test {
            viewModel.toggleFavorite(product)
            val event = awaitItem()
            assert(event is ProductListViewModel.UiEvent.ShowError)
            assertEquals("Error updating favorites", (event as ProductListViewModel.UiEvent.ShowError).message)
        }
    }
}
