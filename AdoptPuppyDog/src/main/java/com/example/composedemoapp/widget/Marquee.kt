package com.example.composedemoapp.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composedemoapp.ui.theme.Pink80

@Composable
fun Marquee(text: String) {
    Text(
        text,
        fontSize = 32.sp,
        color = Pink80,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(vertical = 24.dp, horizontal = 16.dp)
    )
}