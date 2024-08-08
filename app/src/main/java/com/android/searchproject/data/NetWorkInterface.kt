package com.android.searchproject.data

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NetWorkInterface {
    @GET("v2/search/image")
    suspend fun getSearch(
        @Header("Authorization") authHeader: String,
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int): SearchResponse
}
