package com.example.giftpanelcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksActivityViewModel
import com.example.giftpanelcompose.data.GiftBean
import com.example.giftpanelcompose.ui.theme.ComposeDemoAppTheme
import com.example.giftpanelcompose.vm.GiftPanelState
import com.example.giftpanelcompose.vm.GiftPanelViewModel
import com.example.giftpanelcompose.vm.GiftWidgetState
import com.example.giftpanelcompose.vm.GiftWidgetViewModel
import com.example.giftpanelcompose.widget.GiftGridList
import com.example.giftpanelcompose.widget.LoadingView

/**
 * Created by wangyi.huohuo on 19/3/24
 * @author wangyi.huohuo@bytedance.com
 */
class DialogActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoAppTheme {
                TestScreen()
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TestScreen() {
    val showDialog = remember { mutableStateOf(false) }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button(onClick = {
            showDialog.value = true
        }) {
            Text(text = "Open GiftPanel")
        }
    }
    if (showDialog.value) {
        Dialog(
            onDismissRequest = { showDialog.value = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart) {
                Column(
                    modifier = Modifier
                        .height(292.dp)
                        .background(
                            Color(0xFF2E2E2E),
                            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                        ),
                    verticalArrangement = Arrangement.Center
                ) {
                    val vm: GiftWidgetViewModel = mavericksActivityViewModel()
                    val request by vm.collectAsState(GiftWidgetState::request)
                    val data by vm.collectAsState(GiftWidgetState::data)
                    val giftVM: GiftPanelViewModel = mavericksActivityViewModel()
                    data?.apply {
                        val realGiftPages = giftPages
                        val pagerState = rememberPagerState(pageCount = { realGiftPages.size })
                        HorizontalPager(state = pagerState) { page ->
                            val pageList =
                                realGiftPages[page].gifts!!.mapIndexed { index, it ->
                                    val bean = GiftBean(
                                        giftId = it.giftID,
                                        leftLogo = it.giftLabelIcon?.uriList?.get(0) ?: "",
                                        iconUrl = it.mImage.uriList[0],
                                        selectedIconUrl = it.mImage.uriList[0],
                                        name = it.giftName,
                                        diamondCount = it.diamondCount,
                                        order = index
                                    )
                                    bean
                                }
                            giftVM.setupData(pageList)
                            val realData by giftVM.collectAsState(GiftPanelState::giftList)
                            GiftGridList(items = realData)
                        }
                    }

                    if (request is Loading || request is Uninitialized) {
                        LoadingView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally),
                            contentAlignment = Alignment.Center
                        )
                    }
                }
            }
        }
    }
}