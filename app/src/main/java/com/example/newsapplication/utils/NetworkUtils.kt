package com.example.newsapplication.utils

import okhttp3.*
import java.io.IOException

object NetworkUtils {
    private val httpClient = OkHttpClient()
    private const val BASE_URL = "https://newsapi.org/v2"
    private const val API_KEY = "1aef82be99c14df793c0de03cdb26889"

    fun fetchArticlesByCategory(category: String, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        val url = "$BASE_URL/top-headlines?category=$category&country=us&apiKey=$API_KEY"

        val request = Request.Builder().url(url).build()

        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onError(e.message ?: "Unknown error occurred")
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { onSuccess(it) }
            }
        })
    }

}

