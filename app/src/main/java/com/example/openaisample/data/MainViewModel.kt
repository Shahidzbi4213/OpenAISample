package com.example.openaisample.data

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openaisample.MyApp
import com.example.openaisample.model.ApiData
import com.example.openaisample.model.Choice
import com.example.openaisample.model.Request
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


// Created by Shahid Iqbal on 2/14/2023.

class MainViewModel : ViewModel() {

    private var _apiData = MutableStateFlow<NetworkResult<ApiData>>(NetworkResult.Empty)
    val apiData get() = _apiData

    fun sendRequest(prompt: String) = viewModelScope.launch {
        try {
            _apiData.value = NetworkResult.Loading

            val response = MyApp.api.getCompletion(Request(prompt))

            Log.d("Dark", "sendRequest: ${response.body()} ")

            _apiData.value =
                if (response.isSuccessful && response.body() != null) NetworkResult.Success(response.body()!!)
                else NetworkResult.Error(error = "Something went wrong")


        } catch (e: Exception) {
            _apiData.value = NetworkResult.Error(error = e.message ?: "Something went wrong")
        }
    }
}