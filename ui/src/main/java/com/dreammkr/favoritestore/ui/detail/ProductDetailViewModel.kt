package com.dreammkr.favoritestore.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dreammkr.favoritestore.domain.model.Product
import com.dreammkr.favoritestore.domain.use_case.GetProductByIdUseCase
import com.dreammkr.favoritestore.domain.use_case.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val productId: Int = checkNotNull(savedStateHandle["productId"])

    private val _state = MutableStateFlow<ProductDetailState>(ProductDetailState.Loading)
    val state: StateFlow<ProductDetailState> = _state.asStateFlow()

    init {
        loadProduct()
    }

    fun loadProduct() {
        viewModelScope.launch {
            _state.value = ProductDetailState.Loading
            getProductByIdUseCase(productId)
                .onSuccess { product ->
                    _state.value = ProductDetailState.Success(product)
                }
                .onFailure { error ->
                    _state.value = ProductDetailState.Error(error.message ?: "Unknown error")
                }
        }
    }

    fun toggleFavorite() {
        val currentState = _state.value
        if (currentState is ProductDetailState.Success) {
            viewModelScope.launch {
                val product = currentState.product
                toggleFavoriteUseCase(product)
                _state.value =
                    ProductDetailState.Success(product.copy(isFavorite = !product.isFavorite))
            }
        }
    }
}

sealed class ProductDetailState {
    object Loading : ProductDetailState()
    data class Success(val product: Product) : ProductDetailState()
    data class Error(val message: String) : ProductDetailState()
}
