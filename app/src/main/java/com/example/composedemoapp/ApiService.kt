package com.example.composedemoapp

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


const val PER_PAGE = 10

/**
 * Created by wangyi.huohuo on 12/3/24
 * @author wangyi.huohuo@bytedance.com
 */
interface ApiService {
    @Headers(
        "Accept:application/json",
        "x-api-key:live_eSvKdtBwaK2veTehggoxsWAfGEDMOE8SrYrBmWOLrFLsbIurkPqx4NwQQLLKcOhD"
    )
    @GET("search")
    suspend fun search(
        @Query("page") page: Int = 0,
        @Query("limit") limit: Int = PER_PAGE,
        @Query("mime_types") mimeType: String = "jpg",
        @Query("size") size: String = "small",
        @Query("has_breeds") hasBreeds: Boolean = true
    ): List<Dog>
}