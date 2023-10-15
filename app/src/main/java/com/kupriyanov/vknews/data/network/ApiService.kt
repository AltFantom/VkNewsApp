package com.kupriyanov.vknews.data.network

import com.kupriyanov.vknews.data.model.NewsFeedResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("newsfeed.getRecommended?v=5.154")
    suspend fun loadRecommendations(
        @Query("access_token") token: String
    ): NewsFeedResponseDto
}