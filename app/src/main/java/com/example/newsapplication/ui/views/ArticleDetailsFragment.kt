package com.example.newsapplication.ui.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.newsapplication.R
import com.example.newsapplication.model.Article


class ArticleDetailsFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var contentTextView: TextView
    private lateinit var openInWebButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Make sure you return the appropriate layout for the ArticleDetailsFragment here
        return inflater.inflate(R.layout.fragment_article_details, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view.findViewById(R.id.ivArticleImage)
        titleTextView = view.findViewById(R.id.tvArticleTitle)
        descriptionTextView = view.findViewById(R.id.tvArticleDescription)
        contentTextView = view.findViewById(R.id.tvArticleContent)
        openInWebButton = view.findViewById(R.id.btnOpenInWeb)

        val selectedArticle: Article? = arguments?.getParcelable("selectedArticle")

        if (selectedArticle == null) {
            Log.d("Debug", "Selected article is null")
            return
        }

        titleTextView.text = selectedArticle.title
        descriptionTextView.text = selectedArticle.description
        contentTextView.text = selectedArticle.content

        Glide.with(this).load(selectedArticle.urlToImage).into(imageView)

        openInWebButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(selectedArticle.url))
            startActivity(intent)
        }
    }

}
