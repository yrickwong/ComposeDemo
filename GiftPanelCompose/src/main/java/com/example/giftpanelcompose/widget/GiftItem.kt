package com.example.giftpanelcompose.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.giftpanelcompose.R
import com.example.giftpanelcompose.data.GiftBean


@Composable
fun GiftItem(gift: GiftBean, onClick: (GiftBean) -> Unit) {
    Box(contentAlignment = Alignment.Center) {
//        if (gift.selected) {
//            SelectedBound()
//        } else {
//
//        }

        Column(Modifier.clickable {
            onClick.invoke(gift)
        }, horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                contentScale = ContentScale.Crop,
                painter = rememberAsyncImagePainter(gift.iconUrl),
                modifier = Modifier
                    .size(56.dp),
                contentDescription = null
            )
            Text(
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontFamily = FontFamily.SansSerif,
                color = Color(0xCCFFFFFF),
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 6.dp, bottom = 6.dp),
                text = gift.name
            )
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ttlive_icon_coin),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    fontSize = 11.sp,
                    color = Color(0xDDFFFFFF),
                    text = gift.diamondCount.toString()
                )
            }
            Spacer(modifier = Modifier.padding(top = 8.dp))
        }
    }

}

@Composable
fun SelectedBound() {
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0x88161823), Color(0x00161823)),
        startY = 0f,
        endY = Float.POSITIVE_INFINITY // 可以根据需要调整渐变的起始点和结束点
    )
    Box(
        contentAlignment = Alignment.BottomStart, modifier = Modifier
            .fillMaxWidth()
            .height(94.dp)
            .background(gradient, shape = RoundedCornerShape(8.dp))
    ) {
        Text(
            textAlign = TextAlign.Center,
            maxLines = 1,
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier.paint(painterResource(id = R.drawable.ttlive_icon_new_gift_to_send)),
            text = "Send"
        )
    }
}