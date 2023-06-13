package com.kupriyanov.vknews

sealed class AuthState {

    object Authorized: AuthState()

    object NotAuthorized: AuthState()

    object Initial: AuthState()
}
