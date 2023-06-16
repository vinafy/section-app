package com.bangkit.section.ui.auth.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import com.bangkit.section.data.Result
import androidx.lifecycle.ViewModelProvider
import com.bangkit.section.R
import com.bangkit.section.ViewModelFactory
import com.bangkit.section.data.preference.UserPreference
import com.bangkit.section.data.response.login.ResponseLogin
import com.bangkit.section.databinding.ActivityLoginBinding
import com.bangkit.section.ui.MainActivity
import com.bangkit.section.ui.auth.register.RegisterActivity


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var progressDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelFactory = ViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
        binding.login.setOnClickListener {
            login()
        }
        binding.register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            navigate(intent)
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun showLoading(state: Boolean) {
        if (state) {
            val builder = AlertDialog.Builder(this)
            builder.setView(R.layout.loading_layout)
            progressDialog = builder.create()
            progressDialog.show()
        } else {
            progressDialog.dismiss()
        }
    }
    private fun processLogin(data: ResponseLogin) {
        if (data.error) {
            Toast.makeText(this, data.message, Toast.LENGTH_SHORT).show()
        } else {
            val token = data.result.token
            val avatar = data.result.profile_picture
            val name = data.result.full_name
            val id = data.result.id_user.toInt()
            val email = binding.edEmail.text.toString()
            UserPreference.saveApiKey(token, avatar,name,email,id,this)
            val intent = Intent(this, MainActivity::class.java)
            navigate(intent)
        }
    }

    private fun navigate(intent: Intent) {
        startActivity(intent)
        this.finish()
    }

    private fun login(){
        val email = binding.edEmail.text
        val password = binding.edPassword.text
        if (email.isNullOrEmpty()){
            showToast("Email Tidak Boleh Kosong")
        }else if (password.isNullOrEmpty()){
            showToast("Password tidak boleh kosong")
        }else{
            val emailText = email.toString()
            val passwordText = password.toString()
            viewModel.login(emailText,passwordText).observe(this){result->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Success -> {
                            showLoading(false)
                            processLogin(result.data)
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
}