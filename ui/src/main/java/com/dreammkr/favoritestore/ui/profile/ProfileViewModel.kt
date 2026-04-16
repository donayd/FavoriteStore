package com.dreammkr.favoritestore.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dreammkr.favoritestore.domain.repository.User
import com.dreammkr.favoritestore.domain.use_case.GetFavoriteProductsUseCase
import com.dreammkr.favoritestore.domain.use_case.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    getFavoriteProductsUseCase: GetFavoriteProductsUseCase
) : ViewModel() {

    private val _userState = MutableStateFlow<UserState>(UserState.Loading)

    private val _favoriteCount = getFavoriteProductsUseCase()

    private val _state = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            combine(_userState, _favoriteCount) { userState, favorites ->
                when (userState) {
                    is UserState.Loading -> ProfileState.Loading
                    is UserState.Error -> ProfileState.Error(userState.message)
                    is UserState.Success -> ProfileState.Success(userState.user, favorites.size)
                }
            }.collect {
                _state.value = it
            }
        }
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            getUserProfileUseCase()
                .onSuccess { user ->
                    _userState.value = UserState.Success(user)
                }
                .onFailure { error ->
                    _userState.value = UserState.Error(error.message ?: "Unknown error")
                }
        }
    }
}

sealed class UserState {
    object Loading : UserState()
    data class Success(val user: User) : UserState()
    data class Error(val message: String) : UserState()
}

sealed class ProfileState {
    object Loading : ProfileState()
    data class Success(val user: User, val favoriteCount: Int) : ProfileState()
    data class Error(val message: String) : ProfileState()
}
