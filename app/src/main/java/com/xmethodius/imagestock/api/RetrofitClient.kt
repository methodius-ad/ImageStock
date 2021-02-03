package com.xmethodius.imagestock.api

import com.xmethodius.imagestock.Config
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient {
    private var retrofit: Retrofit? = null
    fun getClient(): Retrofit? {
        if (retrofit == null) {
            val client = OkHttpClient.Builder()
                .addInterceptor(MainInterceptor(Config.base_access_key)).build()
            retrofit = Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}