package com.example.newsapplication.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapplication.utils.PreferencesHelper
import com.example.newsapplication.R
import com.example.newsapplication.model.Article
import com.example.newsapplication.ui.views.ArticleDetailsFragment

class ArticlesAdapter(
    private val articles: List<Article>,
    private val onArticleClicked: (Article) -> Unit
) : RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivArticleImage: ImageView = itemView.findViewById(R.id.ivArticleImage)
        val tvArticleTitle: TextView = itemView.findViewById(R.id.tvArticleTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.tvArticleTitle.text = article.title
        Glide.with(holder.itemView.context)
            .load(article.urlToImage)
            .into(holder.ivArticleImage)

        holder.itemView.setOnClickListener {
            // Increment the read count
            PreferencesHelper.incrementReadArticlesCount(it.context)

            val detailsFragment = ArticleDetailsFragment()

            val bundle = Bundle().apply {
                putParcelable("selectedArticle", article)
            }
            detailsFragment.arguments = bundle

            val transaction = (it.context as FragmentActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, detailsFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun getItemCount() = articles.size
}
