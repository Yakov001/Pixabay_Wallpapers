package com.example.pixabaywallpapers.model

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET("?key=$API_KEY&orientation=vertical")
    suspend fun requestCategory(
        @Query("category") category: String
    ) : Response<CategoryResponse>

    companion object {
        var userApi: PixabayApi? = null
        fun getInstance() : PixabayApi {
            if (userApi == null) {
                userApi = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PixabayApi::class.java)
            }
            return userApi!!
        }
        private const val BASE_URL = "https://pixabay.com/api/"
        private const val API_KEY = "33106230-b104905cd7ff74ed17e2229af"
    }
}