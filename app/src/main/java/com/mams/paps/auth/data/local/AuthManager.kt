package com.mams.paps.auth.data.local

class AuthManager {

    // TODO: Use DataStore and flows
    var state: AuthState = AuthState.Unauthenticated
}

sealed interface AuthState {
    data object Unauthenticated : AuthState
    data object Guest : AuthState
    data object Authenticated : AuthState
}
