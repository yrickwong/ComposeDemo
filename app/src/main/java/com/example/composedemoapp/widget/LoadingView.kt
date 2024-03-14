package com.example.composedemoapp.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun LoadingView(modifier: Modifier, contentAlignment: Alignment) {
    Box(modifier = modifier, contentAlignment = contentAlignment) {
        CircularProgressIndicator()
    }
}
