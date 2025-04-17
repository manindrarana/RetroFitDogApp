package com.example.retrofitdogapp.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitdogapp.network.dogApiService
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _dogBreedsState = mutableStateOf(DogBreedsState())
    val dogBreedsState: State<DogBreedsState> = _dogBreedsState

    init {
        fetchDogBreeds()
    }

    private fun fetchDogBreeds() {
        viewModelScope.launch {
            try {
                val response = dogApiService.getDogBreeds()
                val allBreeds = response.message.keys.toList()
                _dogBreedsState.value = DogBreedsState(breeds = allBreeds, loading = false)
            } catch (e: Exception) {
                _dogBreedsState.value = DogBreedsState(loading = false, error = "Error fetching dog breeds: ${e.message}")
            }
        }
    }

    fun randomDogImageState(breed: String): State<RandomDogImageState> {
        val randomDogImageState = mutableStateOf(RandomDogImageState())

        viewModelScope.launch {
            randomDogImageState.value = RandomDogImageState(loading = true)
            try {
                val response = dogApiService.getRandomDogImage(breed)
                randomDogImageState.value = RandomDogImageState(imageUrl = response.message, loading = false)
            } catch (e: Exception) {
                randomDogImageState.value = RandomDogImageState(error = "Error fetching random image: ${e.message}")
            }
        }

        return randomDogImageState
    }

    data class DogBreedsState(
        val breeds: List<String> = emptyList(),
        val loading: Boolean = true,
        val error: String? = null
    )

    data class RandomDogImageState(
        val imageUrl: String = "",
        val loading: Boolean = false,
        val error: String? = null
    )
}

