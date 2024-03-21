package com.example.giftpanelcompose.data

/**
 * Created by wangyi.huohuo on 14/3/24
 * @author wangyi.huohuo@bytedance.com
 */
interface IRepository {
    suspend fun getGiftData(): HttpResult
}