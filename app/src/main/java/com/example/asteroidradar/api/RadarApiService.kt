package com.example.asteroidradar.api

import com.example.asteroidradar.Constants.BASE_URL
import com.example.asteroidradar.PictureOfDay
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

//enum class AsteroidApiFilter(val value: String) { START_DATE("start_date"), END_DATE("end_date")}


private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface AsteroidRadarApiService {
    @GET("neo/rest/v1/feed?api_key=iyR6Asw2WIxVZddxGcgQU4TEvH2mJ2GBiMiRLwTP")
    suspend fun getAsteroids(
        @QueryMap queryMap: Map<String, String>
    ): String
}

object AsteroidRadarApi {
    val retrofitService : AsteroidRadarApiService by lazy {
        retrofit.create(AsteroidRadarApiService::class.java)
    }
}



private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofitImage = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


object AsteroidImageOTDApi {
    val retrofitService : AsteroidImageOTDApiService by lazy {
        retrofitImage.create(AsteroidImageOTDApiService::class.java)
    }
}

interface AsteroidImageOTDApiService {

    @GET("planetary/apod?api_key=iyR6Asw2WIxVZddxGcgQU4TEvH2mJ2GBiMiRLwTP")
    suspend fun getImageOfTheDay(): PictureOfDay
}