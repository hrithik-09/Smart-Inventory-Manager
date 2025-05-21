package com.rkdigital.stocklytics

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rkdigital.stocklytics.model.LoginResponse
import com.rkdigital.stocklytics.ViewModel.LoginViewModel
import com.rkdigital.stocklytics.ViewModel.LoginViewModelFactory
import com.rkdigital.stocklytics.databinding.ActivityLoginBinding
import com.rkdigital.stocklytics.repository.AuthRepository
import com.rkdigital.stocklytics.serviceApi.RetrofitInstance
import com.rkdigital.stocklytics.storage.SharedPreferenceHelper
import com.rkdigital.stocklytics.utils.SharedPrefKeys

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val apiService = RetrofitInstance.apiService
        val authRepository = AuthRepository(apiService)
        val factory = LoginViewModelFactory(authRepository)

        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Email and password must not be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginViewModel.login(email, password)
        }

        loginViewModel.loginResponse.observe(this) { result ->
            result.onSuccess { user ->
                Toast.makeText(this, "Welcome ${user.user.name}", Toast.LENGTH_SHORT).show()
                saveUserSession(user)

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }.onFailure {
                Toast.makeText(this, "Login failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun saveUserSession(user: LoginResponse) {
        val prefsHelper = SharedPreferenceHelper.getInstance(this)
        val userPrefs = prefsHelper.getUserPrefs()

        prefsHelper.putString(userPrefs, SharedPrefKeys.USER_NAME, user.user.name)
        prefsHelper.putString(userPrefs, SharedPrefKeys.USER_ID, user.user.id.toString())
        prefsHelper.putString(userPrefs, SharedPrefKeys.EMAIL, user.user.email)
        prefsHelper.putString(userPrefs, SharedPrefKeys.ROLE, user.user.role)
        prefsHelper.putString(userPrefs, SharedPrefKeys.TOKEN, user.token)
        prefsHelper.putBoolean(userPrefs, SharedPrefKeys.IS_LOGGED_IN, true)
    }

}