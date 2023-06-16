package com.bangkit.section

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bangkit.section.databinding.ActivityReadArticleBinding
import com.bangkit.section.ui.auth.login.LoginActivity

class ReadArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReadArticleBinding
    private lateinit var mToolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReadArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val title = intent.getStringExtra(Title)
        val article = intent.getStringExtra(Article)
        setSupportActionBar(findViewById(R.id.toolbar))
        Log.e(TAG, "onCreate: $title", )
        mToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mToolbar)
        mToolbar.title = title
        mToolbar.setNavigationOnClickListener {
            finish()
        }
        binding.article.article?.text = article
    }
    companion object{
        const val Title = "Title"
        const val Article = "Article"
    }
}