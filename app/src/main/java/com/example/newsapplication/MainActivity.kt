package com.example.newsapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newsapplication.ui.views.NewsTopicsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if savedInstanceState is null. If yes, then it's the first time the activity is being created
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, NewsTopicsFragment())
                .commit()
        }
    }
}
