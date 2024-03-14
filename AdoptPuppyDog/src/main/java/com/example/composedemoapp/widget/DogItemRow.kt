package com.example.composedemoapp.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.composedemoapp.Dog

/**
 * Created by wangyi.huohuo on 12/3/24
 * @author wangyi.huohuo@bytedance.com
 */

@Composable
fun DogCardItem(dog: Dog, navigateToPuppyDetails: (Dog) -> Unit) {
    //Similar the CardView
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(16.dp)
            .clickable { navigateToPuppyDetails(dog) }
    ) {
        //Similar the LinearLayout
        Column {
            Image(
                contentScale = ContentScale.Crop,
                painter = rememberAsyncImagePainter(dog.url),
                modifier = Modifier
                    .height(240.dp)
                    .background(color = Color.Gray)
                    .fillMaxWidth(),
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(top = 12.dp, start = 12.dp),
                text = dog.dogName,
                style = typography.h6,
            )
            Text(
                modifier = Modifier.padding(top = 4.dp, start = 12.dp),
                text = dog.lifeSpan,
                style = typography.body2,
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
