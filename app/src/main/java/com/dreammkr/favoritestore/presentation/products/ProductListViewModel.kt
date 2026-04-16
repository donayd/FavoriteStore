package com.dreammkr.favoritestore.presentation.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dreammkr.favoritestore.domain.model.Product
import com.dreammkr.favoritestore.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ProductListState>(ProductListState.Loading)
    val state: StateFlow<ProductListState> = _state.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _state.value = ProductListState.Loading
            repository.getProducts()
                .onSuccess { products ->
                    _state.value = ProductListState.Success(products)
                }
                .onFailure { error ->
                    _state.value = ProductListState.Error(error.message ?: "Unknown error")
                }
        }
    }

    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            repository.toggleFavorite(product)
            // Refresh list to show updated favorite status
            val currentState = _state.value
            if (currentState is ProductListState.Success) {
                val updatedList = currentState.products.map {
                    if (it.id == product.id) it.copy(isFavorite = !it.isFavorite) else it
                }
                _state.value = ProductListState.Success(updatedList)
            }
        }
    }
}

sealed class ProductListState {
    object Loading : ProductListState()
    data class Success(val products: List<Product>) : ProductListState()
    data class Error(val message: String) : ProductListState()
}
