package com.example.newsapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Article(
    val title: String,
    val urlToImage: String,
    val url: String,
    val author: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val content: String? = null
): Parcelable
