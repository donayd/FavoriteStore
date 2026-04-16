package com.dreammkr.favoritestore.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dreammkr.favoritestore.domain.model.Product
import com.dreammkr.favoritestore.domain.use_case.GetFavoriteProductsUseCase
import com.dreammkr.favoritestore.domain.use_case.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteProductsUseCase: GetFavoriteProductsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    val uiState: StateFlow<FavoritesUiState> = getFavoriteProductsUseCase()
        .map { products ->
            if (products.isEmpty()) {
                FavoritesUiState.Empty
            } else {
                FavoritesUiState.Success(products)
            }
        }
        .onStart { emit(FavoritesUiState.Loading) }
        .catch { e -> emit(FavoritesUiState.Error(e.message ?: "Unknown error")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FavoritesUiState.Loading
        )

    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            toggleFavoriteUseCase(product)
        }
    }
}

sealed class FavoritesUiState {
    object Loading : FavoritesUiState()
    object Empty : FavoritesUiState()
    data class Success(val products: List<Product>) : FavoritesUiState()
    data class Error(val message: String) : FavoritesUiState()
}
