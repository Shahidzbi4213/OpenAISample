package com.example.openaisample.data


// Created by Shahid Iqbal on 2/14/2023.

sealed class NetworkResult<out T> {

    object Empty : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
    data class Error<out T>(var error: String) : NetworkResult<T>()
    data class Success<out T>(val data: T) : NetworkResult<T>()
}