package com.example.myapplication2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ShibeApiService {

    @GET("shibes")
    fun getShibeData(@Query("page") page: Int): Call<List<String>>
}

