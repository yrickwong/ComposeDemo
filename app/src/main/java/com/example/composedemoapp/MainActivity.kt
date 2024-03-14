package com.example.composedemoapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.compose.collectAsState
import com.airbnb.mvrx.compose.mavericksActivityViewModel
import com.example.composedemoapp.ui.theme.ComposeDemoAppTheme
import com.example.composedemoapp.widget.DogCardItem
import com.example.composedemoapp.widget.ErrorView
import com.example.composedemoapp.widget.Loader
import com.example.composedemoapp.widget.LoadingView
import com.example.composedemoapp.widget.Marquee

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoAppTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "home") {
                    composable("home") {
                        DogListHomeScreen(navController)
                    }
                    composable("dog/{id}") { backStackEntry ->
                        val dogId = backStackEntry.arguments?.getString("id")
                        DogDetailScreen(dogId = dogId) {
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
    }
}

// Declarative UI
// Implement a web image app that can drop down to refresh & load more
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DogListHomeScreen(navController: NavHostController) {
    Log.d("wangyi", "PostsScreen: ")
    val dogViewModel: DogViewModel = mavericksActivityViewModel()
    val request by dogViewModel.collectAsState(DogState::request)
    val dogs by dogViewModel.collectAsState(DogState::dogs)
    val refreshing by dogViewModel.collectAsState(DogState::isRefreshing)
    val pullRefreshState = rememberPullRefreshState(refreshing, { dogViewModel.refresh() })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn {
            item {
                Marquee("Showcase")
            }
            itemsIndexed(dogs) { index, data ->
                DogCardItem(
                    data
                ) {
                    navController.navigate("dog/${it.id}")
                }
                // Trigger load more when user scrolls to the end of the list
                if (index == dogs.size - 1 && request !is Loading) {
                    dogViewModel.loadMoreDogs()
                }
            }
        }
        PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        when {
            request is Uninitialized || dogs.isEmpty() -> {
                Loader(modifier = Modifier.align(Alignment.Center))
            }

            request is Loading -> {
                LoadingView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.BottomCenter), contentAlignment = Alignment.Center
                )
            }

            request is Fail -> {
                ErrorView(modifier = Modifier.align(Alignment.Center), dogViewModel = dogViewModel)
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DogDetailScreen(dogId: String?, navigateBack: () -> Unit) {
    val viewModel: DogViewModel = mavericksActivityViewModel()
    val dogList by viewModel.collectAsState(DogState::dogs)
    val dog: Dog = dogList.firstOrNull { it.id == dogId } ?: error("Cannot find dog with id $dogId")
    Scaffold {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxHeight()) {
            Box {
                Column {
                    Image(
                        contentScale = ContentScale.Crop,
                        painter = rememberAsyncImagePainter(dog.url),
                        modifier = Modifier
                            .height(360.dp)
                            .background(color = Color.Gray)
                            .fillMaxWidth(),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(top = 12.dp, start = 12.dp),
                        text = dog.dogName,
                        style = MaterialTheme.typography.h6,
                    )
                    Text(
                        modifier = Modifier.padding(top = 4.dp, start = 12.dp),
                        text = dog.lifeSpan,
                        style = MaterialTheme.typography.h5,
                    )
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Text(
                            modifier = Modifier.padding(top = 4.dp, start = 12.dp),
                            text = dog.temperament,
                            style = TextStyle(fontSize = 28.sp)
                        )
                    }
                }
                Button(
                    onClick = {
                        navigateBack()
                    }, modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(12.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "I love this dog!", style = TextStyle(fontSize = 18.sp))
                }
            }
        }
    }
}