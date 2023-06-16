package com.bangkit.section.ui.auth.register

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.bangkit.section.R
import com.bangkit.section.ViewModelFactory
import com.bangkit.section.data.Result
import com.bangkit.section.data.response.register.ResponseRegister
import com.bangkit.section.databinding.ActivityRegisterBinding
import com.bangkit.section.ui.auth.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var mToolbar: Toolbar
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = resources.getColor(R.color.pink)
        mToolbar = findViewById(R.id.mToolbar)
        setSupportActionBar(mToolbar)
        mToolbar.setNavigationOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        viewModelFactory = ViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[RegisterViewModel::class.java]
        binding.btnRegister.setOnClickListener {
            register()
        }
        binding.login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showLoading(state: Boolean) {
        val builder = AlertDialog.Builder(this)
        builder.setView(R.layout.loading_layout)
        val progressDialog = builder.create()
        if (state) {
            progressDialog.show()
        } else {
            progressDialog.hide()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun register() {
        val name = binding.edRegisterName.text
        val email = binding.edRegisterEmail.text
        val password = binding.edRegisterPassword.text
        if (name.isNullOrEmpty()) {
            showToast("Isi nama terlebih dahulu")
        } else if (email.isNullOrEmpty()) {
            showToast("Email Tidak Boleh Kosong")
        } else if (password.isNullOrEmpty()) {
            showToast("Password tidak boleh kosong")
        } else {
            val nameText = name.toString()
            val emailText = email.toString()
            val passwordText = password.toString()
            viewModel.postRegister(nameText, emailText, passwordText).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Success -> {
                            showLoading(false)
                            processRegister(result.data)
                        }

                        is Result.Error -> {
                            showLoading(false)
                            showToast(result.error)
                        }
                    }
                }
            }
        }
    }

    private fun processRegister(data: ResponseRegister) {
        if (data.error) {
            showToast(data.message)
        } else {
            showToast(data.message)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}