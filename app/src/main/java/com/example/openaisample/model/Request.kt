package com.example.openaisample.model

data class Request(
    val prompt: String,
    val model: String = "text-davinci-003",
    val max_tokens: Int = 200
)
