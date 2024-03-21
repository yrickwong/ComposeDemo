package com.example.giftpanelcompose.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by wangyi.huohuo on 15/3/24
 * @author wangyi.huohuo@bytedance.com
 */
@Parcelize
data class GiftBean(
    val order: Int,
    val giftId: Long = 0,
    val leftLogo: String = "",
    val selected: Boolean = false,
    val iconUrl: String,
    val selectedIconUrl: String,
    val name: String,
    val diamondCount: Long
) : Parcelable