package com.example.composedemoapp

import android.app.Application
import com.airbnb.mvrx.Mavericks
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by wangyi.huohuo on 12/3/24
 * @author wangyi.huohuo@bytedance.com
 */
class DemoApplication:Application() {


    override fun onCreate() {
        super.onCreate()
        Mavericks.initialize(this)
        startKoin {
            androidContext(this@DemoApplication)
            modules(dogServiceModule)
        }
    }
}


private val dogServiceModule = module {
    factory {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    factory {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get<Moshi>()))
            .build()
    }

    single {
        get<Retrofit>().create(ApiService::class.java)
    }
}


/**
 *  Sample like this
 *  https://api.thedogapi.com/v1/images/search?page=0&limit=5&mime_types=jpg&size=small
 */

const val BASE_URL = "https://api.thedogapi.com/v1/images/"