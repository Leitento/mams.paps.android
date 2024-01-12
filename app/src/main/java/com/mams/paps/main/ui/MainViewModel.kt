package com.mams.paps.main.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mams.paps.MamsPapsApplication
import com.mams.paps.auth.data.local.AuthManager
import com.mams.paps.auth.data.local.AuthState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val authManager: AuthManager,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent: Flow<UiEvent> = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            val authState = authManager.state.first { it !is AuthState.Initializing }
            if (authState is AuthState.Authenticated) {
                _uiState.update {
                    it.copy(
                        locationName = "Москва",
                        userFirstName = "Пользователь",
                        isGuest = authState.isGuest
                    )
                }
                _uiEvent.send(UiEvent.NavigateToHome)
            } else if (authState is AuthState.Unauthenticated) {
                _uiEvent.send(UiEvent.NavigateToOnboarding)
            }
        }
    }

    fun logOut() {
        authManager.setUnauthenticated()
        viewModelScope.launch {
            _uiEvent.send(UiEvent.NavigateToAuth)
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val authManager = (this[APPLICATION_KEY] as MamsPapsApplication).authManager
                MainViewModel(
                    authManager = authManager,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}

data class UiState(
    val locationName: String = "",
    val userFirstName: String = "",
    val isGuest: Boolean = false
)

sealed interface UiEvent {
    data object NavigateToOnboarding : UiEvent
    data object NavigateToAuth : UiEvent
    data object NavigateToHome : UiEvent
}
