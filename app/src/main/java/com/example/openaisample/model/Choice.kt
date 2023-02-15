package com.example.openaisample.model

data class Choice(
    val finish_reason: String,
    val index: Int,
    val logprobs: Any,
    val text: String
)