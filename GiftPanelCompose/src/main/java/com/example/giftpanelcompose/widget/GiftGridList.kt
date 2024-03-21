package com.example.giftpanelcompose.widget

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.airbnb.mvrx.compose.mavericksActivityViewModel
import com.example.giftpanelcompose.data.GiftBean
import com.example.giftpanelcompose.vm.GiftPanelViewModel

/**
 * Created by wangyi.huohuo on 15/3/24
 * @author wangyi.huohuo@bytedance.com
 */
@Composable
fun GiftGridList(items: List<GiftBean>) {
    val giftViewModel: GiftPanelViewModel = mavericksActivityViewModel()
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        contentPadding = PaddingValues(top = 8.dp, start = 4.dp, end = 4.dp, bottom = 8.dp),
        content = {
            items(items) { gift ->
                GiftItem(gift = gift, onClick = {
                    if (it.selected) {
                        giftViewModel.onItemClick(it)
                    } else {
                        giftViewModel.onItemSelected(it)
                    }
                })
            }
        })

}