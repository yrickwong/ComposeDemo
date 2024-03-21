package com.example.giftpanelcompose

import android.app.Application
import android.content.Context
import com.airbnb.mvrx.Mavericks
import com.example.giftpanelcompose.data.GiftRepository
import com.example.giftpanelcompose.data.IRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Created by wangyi.huohuo on 14/3/24
 * @author wangyi.huohuo@bytedance.com
 */
class MyApplication : Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        Mavericks.initialize(this)
        startKoin {
            androidContext(androidContext = this@MyApplication)
            modules(API_SERVICE_MODULE)
        }
    }
}

//DI
private val API_SERVICE_MODULE: Module = module {
    factory {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    factory {
        GiftRepository()
    }
    // single instance of GiftRepository
    single<IRepository> { GiftRepository() }
}