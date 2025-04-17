package com.example.retrofitdogapp.network

import com.example.retrofitdogapp.model.DogBreedsResponse
import com.example.retrofitdogapp.model.RandomDogImageResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://dog.ceo/api/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val dogApiService: DogServiceApi = retrofit.create(DogServiceApi::class.java)

interface DogServiceApi {
    @GET("breeds/list/all")
    suspend fun getDogBreeds(): DogBreedsResponse

    @GET("breed/{breed}/images/random")
    suspend fun getRandomDogImage(@Path("breed") breed: String): RandomDogImageResponse
}