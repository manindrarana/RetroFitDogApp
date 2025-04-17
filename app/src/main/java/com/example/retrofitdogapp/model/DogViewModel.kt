package com.example.retrofitdogapp.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitdogapp.network.dogApiService
import kotlinx.coroutines.launch

class DogViewModel : ViewModel() {

    private val _dogBreedsState = mutableStateOf(DogBreedsState())
    val dogBreedsState: State<DogBreedsState> = _dogBreedsState

    private val _randomDogImageState = mutableStateOf(RandomDogImageState())
    val randomDogImageState: State<RandomDogImageState> = _randomDogImageState

    init {
        fetchDogBreeds()
    }

    private fun fetchDogBreeds() {
        viewModelScope.launch {
            try {
                val response = dogApiService.getDogBreeds()
                val breeds = response.message.keys.toList()
                _dogBreedsState.value = DogBreedsState(breeds = breeds, loading = false)
            } catch (e: Exception) {
                _dogBreedsState.value = DogBreedsState(error = "Error: ${e.message}")
            }
        }
    }

    fun fetchRandomDogImage(breed: String) {
        viewModelScope.launch {
            try {
                _randomDogImageState.value = RandomDogImageState(loading = true)
                val response = dogApiService.getRandomDogImage(breed)
                _randomDogImageState.value = RandomDogImageState(imageUrl = response.message)
            } catch (e: Exception) {
                _randomDogImageState.value = RandomDogImageState(error = "Error: ${e.message}")
            }
        }
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

