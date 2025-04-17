package com.example.retrofitdogapp.model

import com.google.gson.annotations.SerializedName

data class DogBreedsResponse(
    @SerializedName("message") val message: Map<String, List<String>>,
    @SerializedName("status") val status: String
)

data class RandomDogImageResponse(
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: String
)

