package com.example.newsapplication.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R

class NewsTopicAdapter(private val topics: List<String>, private val onClickListener: (String) -> Unit) : RecyclerView.Adapter<NewsTopicAdapter.ViewHolder>() {

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val topicTextView: TextView = view.findViewById(R.id.topicTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news_topic, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = topics.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val topic = topics[position]
        holder.topicTextView.text = topic
        holder.view.setOnClickListener { onClickListener(topic) }
    }
}
