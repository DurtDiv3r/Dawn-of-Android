package com.islaharper.dawnofandroid.util

// Handles ApiResponse
sealed class Resource<out Any> {
    object Idle : Resource<Nothing>()
    object Loading : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String, val ex: Throwable? = null) : Resource<Nothing>()
}
