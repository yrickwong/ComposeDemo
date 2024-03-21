package com.example.giftpanelcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksActivityViewModel
import com.example.giftpanelcompose.data.GiftBean
import com.example.giftpanelcompose.ui.theme.ComposeDemoAppTheme
import com.example.giftpanelcompose.vm.GiftWidgetState
import com.example.giftpanelcompose.vm.GiftWidgetViewModel
import com.example.giftpanelcompose.widget.GiftGridList
import com.example.giftpanelcompose.widget.LoadingView


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoAppTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = AppRoute.MainScreen.route) {
                    composable(route = AppRoute.MainScreen.route) {
                        MainScreen(navController)
                    }
                    composable(AppRoute.GiftPanel.route) { backStackEntry ->
                        GiftPanelDialog(navController)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun GiftPanelDialog(navController: NavHostController) {
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Expanded)
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(sheetState = state, sheetContent = {
        val vm: GiftWidgetViewModel = mavericksActivityViewModel()
        val request by vm.collectAsState(GiftWidgetState::request)
        val data by vm.collectAsState(GiftWidgetState::data)

        data?.apply {
            val realGiftPages = giftPages
            Log.d("wangyi", "MainScreen: realGiftPages size=${realGiftPages.size}")
            val pagerState = rememberPagerState(pageCount = { realGiftPages.size })
            HorizontalPager(state = pagerState) { page ->
                val eachGridGifts = realGiftPages[page].gifts!!.mapIndexed { index, it ->
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
                GiftGridList(items = eachGridGifts)
            }
        }
        if (request is Loading || request is Uninitialized) {
            LoadingView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally), contentAlignment = Alignment.Center
            )
        }
    }) {

    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button(modifier = Modifier.padding(16.dp), onClick = {
            navController.navigate(AppRoute.GiftPanel)
        }) {
            Text(text = "Open GiftPanel")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeDemoAppTheme {
        Greeting("Android")
    }
}