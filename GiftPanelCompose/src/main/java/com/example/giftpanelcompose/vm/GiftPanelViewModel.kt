package com.example.giftpanelcompose.vm

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.example.giftpanelcompose.data.GiftBean

/**
 * Created by wangyi.huohuo on 19/3/24
 * @author wangyi.huohuo@bytedance.com
 */

data class GiftPanelState(
    val giftList: List<GiftBean> = emptyList(),
    val lastSelectedId: Long = -1
) : MavericksState

class GiftPanelViewModel(state: GiftPanelState) : MavericksViewModel<GiftPanelState>(state) {

    fun onItemSelected(data: GiftBean) {
        setState {
            copy(
                giftList = giftList.map {
                    if (it.giftId == data.giftId) {
                        it.copy(selected = true)
                    } else {
                        it.copy(selected = false)
                    }
                }
            )
        }
    }

    fun onItemClick(data: GiftBean) {
        setState {
            copy(
                lastSelectedId = data.giftId
            )
        }
    }

    fun setupData(initData: List<GiftBean>) {
        setState {
            copy(
                giftList = initData
            )
        }
    }

}