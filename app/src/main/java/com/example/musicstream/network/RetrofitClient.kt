package com.example.musicstream.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Discovery node kuu ya Audius. Kama haifanyi kazi siku fulani (nodes hubadilika),
    // badilisha na moja ya hizi mbadala:
    // https://discoveryprovider2.audius.co/
    // https://discoveryprovider3.audius.co/
    // https://audius-discovery-1.altego.net/
    var currentHost: String = "https://discoveryprovider.audius.co/"
        private set

    private fun buildRetrofit(host: String): Retrofit {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(host)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    var api: AudiusApi = buildRetrofit(currentHost).create(AudiusApi::class.java)
        private set

    // Endapo host ya sasa itashindikana wakati wa 'search', tumia hii kubadili node
    fun switchHost(newHost: String) {
        currentHost = newHost
        api = buildRetrofit(currentHost).create(AudiusApi::class.java)
    }
}
