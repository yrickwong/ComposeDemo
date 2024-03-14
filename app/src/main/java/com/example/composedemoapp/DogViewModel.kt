package com.example.composedemoapp

import android.util.Log
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.android.inject


data class DogState(
    val isRefreshing: Boolean = false,
    val dogs: List<Dog> = emptyList(),
    val request: Async<List<Dog>> = Uninitialized,
    val lovedDogId: String? = null,
) : MavericksState {
    val lovedDog: Dog? = dog(lovedDogId)
    private fun dog(dogId: String?): Dog? = dogs.firstOrNull { it.id == dogId }
}

/**
 * Created by wangyi.huohuo on 12/3/24
 * @author wangyi.huohuo@bytedance.com
 */
class DogViewModel(dogState: DogState, private val apiService: ApiService) :
    MavericksViewModel<DogState>(dogState) {

    init {
        fetchData()
    }

    private fun fetchData() {
        Log.d("wangyi", "call fetchData")
        withState { state ->
            if (state.request is Loading) return@withState //avoid duplicate request
            suspend {
                apiService.search(page = 0)
            }.execute(Dispatchers.IO) {
                copy(
                    request = it,
                    dogs = (it() ?: emptyList())
                )
            }
        }

    }

    fun retryLoadingDogs() {
        fetchData()
    }

    /**
     * trigger loadMore
     */
    fun loadMoreDogs() {
        Log.d("wangyi", "loadMoreDogs")
        withState { state ->
            if (state.request is Loading) return@withState //avoid duplicate request
            suspend {
                apiService.search(
                    page = state.dogs.size / PER_PAGE + 1,
                )
            }.execute(Dispatchers.IO) {
                copy(
                    request = it,
                    dogs = dogs + (it() ?: emptyList())
                )
            }
        }
    }

    fun refresh() {
        fetchData()
    }

    companion object : MavericksViewModelFactory<DogViewModel, DogState> {

        override fun create(viewModelContext: ViewModelContext, state: DogState): DogViewModel {
            val service: ApiService by viewModelContext.activity.inject()
            return DogViewModel(state, service)
        }
    }
}