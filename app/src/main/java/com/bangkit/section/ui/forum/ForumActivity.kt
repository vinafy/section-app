package com.bangkit.section.ui.forum

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.section.R
import com.bangkit.section.ViewModelFactory
import com.bangkit.section.databinding.ActivityForumBinding
import com.bangkit.section.ui.adapter.ForumAdapter
import com.bangkit.section.ui.auth.login.LoginViewModel
import kotlinx.coroutines.launch

class ForumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForumBinding
    private lateinit var viewModel: ForumViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var forumAdapter: ForumAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelFactory = ViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[ForumViewModel::class.java]
        forumAdapter = ForumAdapter()
        binding.rvforum.adapter = forumAdapter
        binding.rvforum.layoutManager = LinearLayoutManager(this)
        viewModel.getStories().observe(this) {
            lifecycleScope.launch {
                forumAdapter.submitData(it)
                forumAdapter.notifyDataSetChanged()
                Log.e(TAG, "onCreate: $it", )
            }
        }
    }
}