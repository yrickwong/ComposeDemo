package com.example.composedemoapp.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.composedemoapp.DogViewModel


@Composable
fun ErrorView(
    dogViewModel:DogViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Failed to load dogs. Please try again.")
        Button(onClick = { dogViewModel.retryLoadingDogs() }) {
            Text("Retry")
        }
    }
}