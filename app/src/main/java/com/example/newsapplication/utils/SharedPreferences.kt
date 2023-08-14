package com.example.newsapplication.utils

import android.content.Context

object PreferencesHelper {

    private const val PREF_NAME = "NewsAppPrefs"
    private const val READ_ARTICLES_COUNT = "ReadArticlesCount"

    fun getReadArticlesCount(context: Context): Int {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(READ_ARTICLES_COUNT, 0)
    }

    fun incrementReadArticlesCount(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val currentCount = getReadArticlesCount(context)
        editor.putInt(READ_ARTICLES_COUNT, currentCount + 1)
        editor.apply()
    }
}
