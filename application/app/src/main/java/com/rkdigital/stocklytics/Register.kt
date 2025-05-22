package com.rkdigital.stocklytics

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.rkdigital.stocklytics.ViewModel.LoginViewModel
import com.rkdigital.stocklytics.ViewModel.LoginViewModelFactory
import com.rkdigital.stocklytics.databinding.ActivityRegisterBinding
import com.rkdigital.stocklytics.repository.AuthRepository
import com.rkdigital.stocklytics.serviceApi.RetrofitInstance

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = LoginViewModelFactory(AuthRepository(RetrofitInstance.apiService))
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
        val roles = arrayOf("Admin", "Staff")
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, roles)
        (binding.etRole as? AutoCompleteTextView)?.setAdapter(adapter)
        binding.btnSignUp.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()
            val role = binding.etRole.text.toString().lowercase()
            println(name)
            println(confirmPassword)
            println(role)
            println(password)
            println(email)
            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.register(name, email, password, role)
        }

        binding.btnLogin.setOnClickListener {
            finish()
        }

        viewModel.registerResponse.observe(this) { result ->
            result.onSuccess {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                finish()
            }.onFailure {
                Toast.makeText(this, "Registration failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }
}