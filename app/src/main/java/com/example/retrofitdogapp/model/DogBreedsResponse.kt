package com.example.retrofitdogapp.model

data class DogBreedsResponse(
    val message: Map<String, List<String>>,
    val status: String
)

