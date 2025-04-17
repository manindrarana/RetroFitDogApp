package com.example.retrofitdogapp.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.retrofitdogapp.model.MainViewModel
import com.example.retrofitdogapp.ui.theme.AppPrimaryColor
import com.example.retrofitdogapp.ui.theme.ButtonColor
import com.example.retrofitdogapp.ui.theme.ButtonTextColor
import com.example.retrofitdogapp.ui.theme.CustomFontFamily
import com.example.retrofitdogapp.ui.theme.TextColor

@Composable
fun DogScreen(onBackPressed: () -> Unit) {
    val viewModel: MainViewModel = viewModel()
    val state by viewModel.dogBreedsState
    var searchQuery by remember { mutableStateOf("") }
    var showResults by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppPrimaryColor)
            .padding(16.dp)
    ) {
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                showResults = searchQuery.isNotBlank()
            },
            label = { Text("Search dog breeds", color = ButtonColor) },
            textStyle = TextStyle(
                fontFamily = CustomFontFamily,
                fontWeight = FontWeight.Normal,
                color = ButtonTextColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.loading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = ButtonColor
                )
                state.error != null -> Text(
                    text = state.error ?: "Unknown error occurred",
                    color = ButtonColor,
                    style = TextStyle(fontFamily = CustomFontFamily, fontWeight = FontWeight.Bold),
                    modifier = Modifier.align(Alignment.Center)
                )
                showResults -> {
                    val filteredBreeds = state.breeds.filter { it.contains(searchQuery, ignoreCase = true) }
                    if (filteredBreeds.isEmpty()) {
                        Text(
                            text = "No results found",
                            color = ButtonColor,
                            style = TextStyle(fontFamily = CustomFontFamily, fontWeight = FontWeight.Bold),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(filteredBreeds) { breed ->
                                DogBreedItem(breed = breed)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DogBreedItem(breed: String) {
    val viewModel: MainViewModel = viewModel()
    val randomDogImageState by viewModel.randomDogImageState(breed)

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(ButtonTextColor.copy(alpha = 0.1f))
            .padding(8.dp)
    ) {
        when {
            randomDogImageState.loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = ButtonColor
                )
            }
            randomDogImageState.error != null -> {
                Text(
                    text = "Error loading image",
                    color = ButtonColor,
                    style = TextStyle(fontFamily = CustomFontFamily, fontWeight = FontWeight.Bold),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            else -> {
                val imageUrl = randomDogImageState.imageUrl
                Log.d("DogBreedItem", "Image URL: $imageUrl")
                if (imageUrl.isNotEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = "Dog Breed Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        Text(
            text = breed,
            color = ButtonColor,
            style = TextStyle(fontFamily = CustomFontFamily, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

