package com.mams.paps.auth.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mams.paps.MamsPapsApplication
import com.mams.paps.auth.data.local.AuthManager
import com.mams.paps.auth.validator.EmailValidator
import com.mams.paps.auth.validator.PasswordValidator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authManager: AuthManager,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent: Flow<UiEvent> = _uiEvent.receiveAsFlow()

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated = _isAuthenticated.asStateFlow()

    fun continueAsGuest() {
        authManager.setAuthenticated(isGuest = true)
        _isAuthenticated.value = true
    }

    fun logIn(email: String, password: String) = viewModelScope.launch {
        if (!EmailValidator.validate(email)) {
            _uiEvent.send(UiEvent.ErrorInvalidEmail)
            return@launch
        }
        when (PasswordValidator.validate(password)) {
            PasswordValidator.Result.ERROR_EMPTY -> {
                _uiEvent.send(UiEvent.ErrorEmptyPassword)
                return@launch
            }
            PasswordValidator.Result.ERROR_TOO_SHORT -> {
                _uiEvent.send(UiEvent.ErrorPasswordTooShort)
                return@launch
            }
            else -> {}
        }
        authManager.setAuthenticated()
        _isAuthenticated.value = true
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val authManager = (this[APPLICATION_KEY] as MamsPapsApplication).authManager
                LoginViewModel(
                    authManager = authManager,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}

sealed interface UiEvent {
    data object ErrorInvalidEmail : UiEvent
    data object ErrorEmptyPassword : UiEvent
    data object ErrorPasswordTooShort : UiEvent
}
