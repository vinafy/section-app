package com.bangkit.section.ui.forum.posting

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.bangkit.section.R
import com.bangkit.section.ViewModelFactory
import com.bangkit.section.data.Result
import com.bangkit.section.data.preference.UserPreference
import com.bangkit.section.databinding.ActivityPostingBinding
import com.bangkit.section.ui.MainActivity
import com.bangkit.section.ui.auth.login.LoginActivity
import com.bangkit.section.ui.auth.login.LoginViewModel
import com.bumptech.glide.Glide

class PostingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostingBinding
    private lateinit var viewModel: PostingViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var mToolbar: Toolbar

    private var selectedTheme: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        mToolbar = findViewById(R.id.mToolbarPosting)
        setSupportActionBar(mToolbar)
        mToolbar.setNavigationOnClickListener {
            this.finish()
        }
        viewModelFactory = ViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[PostingViewModel::class.java]
        val items = arrayOf("Edukasi", "Pengalaman", "Cerita")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items)
        binding.tema.setAdapter(adapter)
        binding.tema.setOnItemClickListener { parent, _, position, _ ->
            selectedTheme = parent.getItemAtPosition(position).toString()
        }
        val sharedPref = UserPreference.initPref(this, "onSignIn")
        val avatarUrl = sharedPref.getString("avatarURL", null).toString()
        val name = sharedPref.getString("name", null).toString()
        val token = sharedPref.getString("apiKey", "anjing gaada")
        Log.e(TAG, "onCreate: $token", )
        binding.tvName.text = name
        Glide.with(this)
            .load(avatarUrl)
            .fitCenter()
            .into(binding.avatar)

    }

    private fun postingDiscussion() {
        val tema = selectedTheme
        val body = binding.body.text.toString()
        val sharedPref = UserPreference.initPref(this, "onSignIn")
        val id = sharedPref.getInt("id", 0)
        if (tema.isEmpty()) {
            showToast("Pilih tema terlebih dahulu")
        } else if (body.isEmpty()) {
            showToast("Tuliskan Sesuatu")
        } else {
            viewModel.postDiscussion(id, tema, body).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {

                        }

                        is Result.Success -> {
                            showToast("Diskusi Berhasil dibuat")
                            finish()
                        }

                        is Result.Error -> {
                            showToast(result.error)
                            Log.e(TAG, "postingDiscussion: ${result.error}", )
                        }
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_nav_menu, menu)
        val customActionLayout = menu.findItem(R.id.posting).actionView
        val button = customActionLayout?.findViewById<Button>(R.id.btnPosting)
        button?.setOnClickListener {
            postingDiscussion()
        }

        return true
    }
}