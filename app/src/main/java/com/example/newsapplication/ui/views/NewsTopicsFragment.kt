package com.example.newsapplication.ui.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.utils.PreferencesHelper
import com.example.newsapplication.R
import com.example.newsapplication.ui.adapters.NewsTopicAdapter
import com.example.newsapplication.ui.viewModel.NewsViewModel

class NewsTopicsFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModels()

    private lateinit var readCountTextView: TextView
    private lateinit var topicsRecyclerView: RecyclerView

    private val topics = listOf("Technology", "Health","Business","Politics")  // Add more sources as per your requirement
    private val topicToSourceMap = mapOf(
        "Technology" to "technology",
        "Health" to "health",
        "Business" to "business",
        "Politics" to "politics"
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news_topic, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        readCountTextView = view.findViewById(R.id.tvReadCount)
        topicsRecyclerView = view.findViewById(R.id.rvTopics)

        // Display the total read count from SharedPreferences here
        // For now, just a placeholder:
        readCountTextView.text = "Total number of read articles: 0"

        val adapter = NewsTopicAdapter(topics) { selectedTopic ->
            val articlesFragment = ArticlesFragment()

            val bundle = Bundle()
            val source = topicToSourceMap[selectedTopic] ?: return@NewsTopicAdapter
            bundle.putString("selectedTopic", source)  // Sending the source instead
            articlesFragment.arguments = bundle

            // Handle fragment transaction
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, articlesFragment)  // Assuming 'container' is the ID of your FragmentContainerView or the FrameLayout that hosts the fragments
            transaction.addToBackStack(null)
            transaction.commit()
        }

        topicsRecyclerView.adapter = adapter
        topicsRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onResume() {
        super.onResume()

        viewModel.loadReadCount(requireContext())
        viewModel.readCount.observe(viewLifecycleOwner) { count ->
            readCountTextView.text = "Total number of read articles: $count"
        }
    }
}
