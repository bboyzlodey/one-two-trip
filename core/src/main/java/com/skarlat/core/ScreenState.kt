package com.skarlat.core

sealed interface ScreenState {
    object Loading : ScreenState
    class Success(val result: Any) : ScreenState
    class Error(val message: String) : ScreenState
}