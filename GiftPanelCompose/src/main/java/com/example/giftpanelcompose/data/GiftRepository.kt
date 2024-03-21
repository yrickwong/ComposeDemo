package com.example.giftpanelcompose.data

import android.app.Application
import com.example.giftpanelcompose.MyApplication
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

/**
 * Created by wangyi.huohuo on 14/3/24
 * @author wangyi.huohuo@bytedance.com
 */
class GiftRepository : IRepository {
    companion object {
        const val RES_PATH = "gift/lists.json"
    }

    override suspend fun getGiftData(): HttpResult {
        return withContext(Dispatchers.IO) {
            delay(1_000L) // Suspend function that replaces Thread.sleep
            val json = MyApplication.appContext.assets.open(RES_PATH).bufferedReader().use {
                it.readText()
            }
            val moshi: Moshi by (MyApplication.appContext as Application).inject()
            val adapter = moshi.adapter(HttpResult::class.java)
            adapter.fromJson(json) ?: throw IllegalStateException("Parsed result is null")
        }
    }
}