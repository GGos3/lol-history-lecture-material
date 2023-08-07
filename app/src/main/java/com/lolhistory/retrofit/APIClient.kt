package com.lolhistory.retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
    fun getRiotClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BaseUrl.RIOT_API_BASE_URL)
            // Json to Gson
            .addConverterFactory(GsonConverterFactory.create())
            // 메인 스레드와 Request 스레드 분리 (메인에서는 UI 처리)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun getRiotV5Client(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BaseUrl.RIOT_API_V5_BASE_URL)
            // Json to Gson
            .addConverterFactory(GsonConverterFactory.create())
            // 메인 스레드와 Request 스레드 분리 (메인에서는 UI 처리)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}