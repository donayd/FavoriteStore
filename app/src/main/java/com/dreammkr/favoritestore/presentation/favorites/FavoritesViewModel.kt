package com.dreammkr.favoritestore.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dreammkr.favoritestore.domain.model.Product
import com.dreammkr.favoritestore.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    val favoriteProducts: StateFlow<List<Product>> = repository.getFavoriteProducts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun toggleFavorite(product: Product) {
        viewModelScope.launch {
            repository.toggleFavorite(product)
        }
    }
}
