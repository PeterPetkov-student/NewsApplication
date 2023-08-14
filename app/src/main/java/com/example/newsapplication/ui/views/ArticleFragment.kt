package com.example.newsapplication.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.utils.NetworkUtils
import com.example.newsapplication.R
import com.example.newsapplication.ui.adapters.ArticlesAdapter
import com.example.newsapplication.model.Article
import com.example.newsapplication.ui.viewModel.NewsViewModel
import org.json.JSONObject
import org.json.JSONException

    class ArticlesFragment : Fragment() {

        private lateinit var articlesRecyclerView: RecyclerView
        private val articlesList = mutableListOf<Article>()

        private val viewModel: NewsViewModel by viewModels()

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_articles, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            articlesRecyclerView = view.findViewById(R.id.rvArticles)
            articlesRecyclerView.layoutManager = LinearLayoutManager(context)

            // Fetch articles from the API
            fetchArticlesByCategory()
        }

        private fun fetchArticlesByCategory() {
            val selectedSource = arguments?.getString("selectedTopic")
            if (selectedSource != null) {
                NetworkUtils.fetchArticlesByCategory(selectedSource,
                    onSuccess = { responseBody ->
                        try {
                            val json = JSONObject(responseBody)
                            val status = json.getString("status")
                            if (status == "ok") {
                                val articlesJsonArray = json.getJSONArray("articles")
                                for (i in 0 until articlesJsonArray.length()) {
                                    val articleObject = articlesJsonArray.getJSONObject(i)
                                    val title = articleObject.getString("title")
                                    val imageUrl = articleObject.getString("urlToImage")
                                    val url = articleObject.getString("url")
                                    val author = articleObject.optString("author") // Use optString to avoid JSONException
                                    val description = articleObject.optString("description")
                                    val publishedAt = articleObject.optString("publishedAt")
                                    val content = articleObject.optString("content")
                                    articlesList.add(Article(title, imageUrl, url, author = author, description = description, publishedAt = publishedAt, content = content))
                                }
                                activity?.runOnUiThread {
                                    val adapter = ArticlesAdapter(articlesList) { selectedArticle ->
                                        viewModel.selectArticle(selectedArticle)
                                    }
                                    articlesRecyclerView.adapter = adapter
                                }
                            } else {
                                val errorMessage = json.getString("message")
                                activity?.runOnUiThread {
                                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                                }
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    },
                    onError = { errorMessage ->
                        activity?.runOnUiThread {
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        }

    }
