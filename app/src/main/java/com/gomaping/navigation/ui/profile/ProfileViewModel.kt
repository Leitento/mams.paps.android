package com.gomaping.navigation.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.gomaping.GoMapingApplication
import com.gomaping.auth.data.local.AuthManager
import com.gomaping.auth.data.local.AuthState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authManager: AuthManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            authManager.state.first {
                it is AuthState.Unauthenticated
            }
            _uiEvent.send(UiEvent.NavigateToAuth)
        }

        viewModelScope.launch {
            // TODO: Use real data
            _uiState.value = UiState(
                photo = "https://source.unsplash.com/user/c_v_r",
                name = "Василий Иванов",
                town = "Москва",
                email = "user@example.com",
                phone = "+7(978)814-74-85",
                birthday = "11.12.2003"
            )
        }
    }

    fun logOut() {
        authManager.setUnauthenticated()
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val authManager = (this[APPLICATION_KEY] as GoMapingApplication).authManager
                ProfileViewModel(
                    authManager = authManager
                )
            }
        }
    }
}

data class UiState(
    val photo: String? = null,
    val name: String = "",
    val town: String = "",
    val email: String = "",
    val phone: String = "",
    val birthday: String = "",
)

sealed interface UiEvent {
    data object NavigateToAuth : UiEvent
}
