package com.example.newsapplication.ui.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapplication.model.Article
import com.example.newsapplication.utils.PreferencesHelper

class NewsViewModel (application: Application) : AndroidViewModel(application) {
    private val _selectedArticle = MutableLiveData<Article?>()
    val selectedArticle: LiveData<Article?> get() = _selectedArticle

    val readCount: LiveData<Int> = MutableLiveData(0)

    fun selectArticle(article: Article) {
        _selectedArticle.value = article
        incrementReadCount()
    }

    private fun incrementReadCount() {
        val currentCount = (readCount.value ?: 0) + 1
        (readCount as MutableLiveData).value = currentCount
        PreferencesHelper.incrementReadArticlesCount(getApplication<Application>().applicationContext)
        // Note: this will require using AndroidViewModel instead of ViewModel
        // since we're trying to access the application context from the ViewModel.
    }

    fun loadReadCount(context: Context) {
        val count = PreferencesHelper.getReadArticlesCount(context)
        (readCount as MutableLiveData).value = count
    }
}