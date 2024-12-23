package com.example.kaizenmockapp.util

sealed interface AsyncParam<out T> {
    data object Loading: AsyncParam<Nothing>
    data class Error(val message: String): AsyncParam<Nothing>
    data class Success<T>(
        val item: T,
        val filtering: Boolean = false,
    ): AsyncParam<T>
}