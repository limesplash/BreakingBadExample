package com.limesplash.breakingbadexample.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class BreakingBadService {
    companion object {
        val URL = "https://breakingbadapi.com"

        fun retrofit(): Retrofit {
            val client = OkHttpClient.Builder()
            return Retrofit.Builder()
                .baseUrl(URL)
                .client(client.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}