package com.dreammkr.favoritestore.presentation.products

import app.cash.turbine.test
import com.dreammkr.favoritestore.domain.model.Product
import com.dreammkr.favoritestore.domain.repository.ProductRepository
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
class ProductListViewModelTest {

    private val repository: ProductRepository = mockk()
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
    fun `loadProducts sets Success state when repository returns products`() = runTest {
        val products = listOf(
            Product(1, "Product 1", 10.0, "Desc", "Cat", "Img", false)
        )
        coEvery { repository.getProducts() } returns Result.success(products)

        viewModel = ProductListViewModel(repository)

        viewModel.state.test {
            val loadingState = awaitItem()
            assert(loadingState is ProductListState.Loading)
            
            val successState = awaitItem()
            assert(successState is ProductListState.Success)
            assertEquals(products, (successState as ProductListState.Success).products)
        }
    }

    @Test
    fun `loadProducts sets Error state when repository returns failure`() = runTest {
        val errorMessage = "Network Error"
        coEvery { repository.getProducts() } returns Result.failure(Exception(errorMessage))

        viewModel = ProductListViewModel(repository)

        viewModel.state.test {
            awaitItem() // Loading
            val errorState = awaitItem()
            assert(errorState is ProductListState.Error)
            assertEquals(errorMessage, (errorState as ProductListState.Error).message)
        }
    }
}
