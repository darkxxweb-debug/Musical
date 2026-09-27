package com.example.musicstream.network

import com.example.musicstream.model.AudiusSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Audius ni mfumo wa bure, wa "open source" wa kusambaza muziki (hauhitaji API key).
// Tunatumia "discovery node" moja kuu; endapo itashindwa, tunaweza kubadili host
// kwenye RetrofitClient.kt (kuna orodha ya nodes mbadala kwenye maoni pale).
interface AudiusApi {

    @GET("v1/tracks/search")
    suspend fun searchTracks(
        @Query("query") query: String,
        @Query("app_name") appName: String = APP_NAME
    ): AudiusSearchResponse

    @GET("v1/tracks/trending")
    suspend fun trendingTracks(
        @Query("app_name") appName: String = APP_NAME
    ): AudiusSearchResponse

    companion object {
        const val APP_NAME = "ClaudeMusicStreamApp"

        // Kiungo cha kucheza (stream) wimbo moja kwa moja - kinatumika na ExoPlayer
        fun streamUrl(host: String, trackId: String): String =
            "$host/v1/tracks/$trackId/stream?app_name=$APP_NAME"
    }
}
