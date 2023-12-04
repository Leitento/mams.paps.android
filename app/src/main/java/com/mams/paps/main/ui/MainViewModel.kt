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
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val authManager: AuthManager,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent: Flow<UiEvent> = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            if (authManager.state == AuthState.Unauthenticated) {
                _uiEvent.send(UiEvent.NavigateToOnboarding)
            } else {
                _uiEvent.send(UiEvent.NavigateToHome)
            }
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

sealed interface UiEvent {
    data object NavigateToOnboarding : UiEvent
    data object NavigateToHome : UiEvent
}
