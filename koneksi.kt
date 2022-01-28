package com.informatika.databarang.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class koneksi {
    companion object{
        var retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.196/dabar/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var service: ApiService = retrofit.create(ApiService::class.java)
    }
}