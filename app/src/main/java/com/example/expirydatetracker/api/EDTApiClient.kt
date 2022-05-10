package com.example.expirydatetracker.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import com.google.gson.GsonBuilder

import com.google.gson.Gson
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


class EDTApiClient {
    companion object{
        private var api: EDTApi? = null
        fun getApi():EDTApi{
            if(api == null){
                val gson = GsonBuilder()
                    .setLenient()
                    .create()

                val okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(90, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .build()

                api = Retrofit.Builder()
                    .baseUrl("https://expiry-date-tracker-database.herokuapp.com/api/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(EDTApi::class.java)
            }
            return api!!
        }
    }
}