package com.dreammkr.favoritestore.presentation.profile

import app.cash.turbine.test
import com.dreammkr.favoritestore.domain.model.Product
import com.dreammkr.favoritestore.domain.repository.User
import com.dreammkr.favoritestore.domain.use_case.GetFavoriteProductsUseCase
import com.dreammkr.favoritestore.domain.use_case.GetUserProfileUseCase
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
class ProfileViewModelTest {

    private val getUserProfileUseCase: GetUserProfileUseCase = mockk()
    private val getFavoriteProductsUseCase: GetFavoriteProductsUseCase = mockk()
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
    fun `state emits Success when use cases return data`() = runTest {
        val user = User(1, "johndoe", "john@email.com", "John Doe", "avatar_url")
        val favorites = listOf(
            Product(1, "Product 1", 10.0, "Desc", "Cat", "Img", true)
        )
        
        coEvery { getUserProfileUseCase() } returns Result.success(user)
        every { getFavoriteProductsUseCase() } returns flowOf(favorites)

        val viewModel = ProfileViewModel(getUserProfileUseCase, getFavoriteProductsUseCase)

        viewModel.state.test {
            // Initial state might be Loading or Success depending on execution speed
            var currentState = awaitItem()
            
            if (currentState is ProfileState.Loading) {
                currentState = awaitItem()
            }
            
            assert(currentState is ProfileState.Success)
            val successState = currentState as ProfileState.Success
            assertEquals(user, successState.user)
            assertEquals(1, successState.favoriteCount)
        }
    }

    @Test
    fun `state emits Error when profile loading fails`() = runTest {
        val errorMessage = "Network Error"
        coEvery { getUserProfileUseCase() } returns Result.failure(Exception(errorMessage))
        every { getFavoriteProductsUseCase() } returns flowOf(emptyList())

        val viewModel = ProfileViewModel(getUserProfileUseCase, getFavoriteProductsUseCase)

        viewModel.state.test {
            var currentState = awaitItem()
            
            if (currentState is ProfileState.Loading) {
                currentState = awaitItem()
            }
            
            assert(currentState is ProfileState.Error)
            assertEquals(errorMessage, (currentState as ProfileState.Error).message)
        }
    }
}
