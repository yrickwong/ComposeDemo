package com.example.giftpanelcompose.vm

import android.util.Log
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import com.example.giftpanelcompose.data.GiftRepository
import com.example.giftpanelcompose.data.GiftResultData
import com.example.giftpanelcompose.data.HttpResult
import com.example.giftpanelcompose.data.IRepository
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.android.inject

/**
 * Created by wangyi.huohuo on 14/3/24
 * @author wangyi.huohuo@bytedance.com
 */
data class GiftWidgetState(
    val data: GiftResultData? = null,
    val request: Async<HttpResult> = Uninitialized
) : MavericksState

class GiftWidgetViewModel(
    private val giftRepository: IRepository,
    state: GiftWidgetState
) : MavericksViewModel<GiftWidgetState>(state) {

    init {
        fetchGiftPanelDataList()
    }

    private fun fetchGiftPanelDataList() {
        Log.d("wangyi", "fetchGiftPanelDataList: ")
        suspend {
            giftRepository.getGiftData()
        }.execute(Dispatchers.IO) {
            copy(
                request = it,
                data = it()?.data
            )
        }
    }

    companion object : MavericksViewModelFactory<GiftWidgetViewModel, GiftWidgetState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: GiftWidgetState
        ): GiftWidgetViewModel {
            val repository: GiftRepository by viewModelContext.activity.inject()
            return GiftWidgetViewModel(repository, state)
        }
    }
}