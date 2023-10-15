package com.kupriyanov.vknews.presentation.main

sealed class AuthState {

    object Authorized: AuthState()

    object NotAuthorized: AuthState()

    object Initial: AuthState()
}
