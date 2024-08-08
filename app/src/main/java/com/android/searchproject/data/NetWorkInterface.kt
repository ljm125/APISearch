package com.android.searchproject.data

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NetWorkInterface {

    //GET은 주소의 세부 주소(이미지 검색이기 때문에 v2/search/image)
    @GET("v2/search/image")
    //요청 변수는 사이트마다 다름, 이번 카카오 같은 경우 Header(API키 : Authorization), Query(query, sort, page, size)로 분류
    suspend fun getSearch(
        @Header("Authorization") authHeader: String,
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int): SearchResponse
}
