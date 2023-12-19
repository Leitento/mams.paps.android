package com.mams.paps.auth.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

class AuthManager(
    private val context: Context,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
) {

    private val _state: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.Initializing)
    val state = _state.asStateFlow()

    init {
        scope.launch {
            context.authDataStore.edit {
                val isAuthenticated = it[KEY_IS_AUTHENTICATED] ?: false
                if (isAuthenticated) {
                    val isGuest = it[KEY_IS_GUEST] ?: true
                    _state.value = AuthState.Authenticated(isGuest)
                } else {
                    _state.value = AuthState.Unauthenticated
                }
            }
        }
    }

    fun setAuthenticated(isGuest: Boolean = false) {
        _state.value = AuthState.Authenticated(isGuest)
        scope.launch {
            context.authDataStore.edit {
                it[KEY_IS_AUTHENTICATED] = true
                it[KEY_IS_GUEST] = isGuest
            }
        }
    }

    fun setUnauthenticated() {
        _state.value = AuthState.Unauthenticated
        scope.launch {
            context.authDataStore.edit { it.clear() }
        }
    }

    companion object {
        private val KEY_IS_AUTHENTICATED = booleanPreferencesKey("isAuthenticated")
        private val KEY_IS_GUEST = booleanPreferencesKey("isGuest")
    }
}

sealed interface AuthState {
    data object Initializing : AuthState
    data object Unauthenticated : AuthState
    data class Authenticated(val isGuest: Boolean) : AuthState
}
