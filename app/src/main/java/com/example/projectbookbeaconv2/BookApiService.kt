package com.example.projectbookbeaconv2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApiService {

    @GET("recomendaciones/{uid}")
    fun getRecommendations(@Path("uid") userId: String): Call<List<BookRecommendation>>

}