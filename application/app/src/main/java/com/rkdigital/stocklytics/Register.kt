package com.rkdigital.stocklytics

import android.os.Bundle
import android.util.Patterns
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
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()
            val role = binding.etRole.text.toString().lowercase()
            val acceptedTerms = binding.termsAndConditions.isChecked


            listOf(binding.etName, binding.etEmail, binding.etPassword,
                binding.etConfirmPassword, binding.etRole).forEach {
                it.error = null
            }

            when {
                name.isEmpty() -> binding.etName.error = "Name required"
                email.isEmpty() -> binding.etEmail.error = "Email required"
                password.isEmpty() -> binding.etPassword.error = "Password required"
                confirmPassword.isEmpty() -> binding.etConfirmPassword.error = "Confirm password"
                role.isEmpty() -> binding.etRole.error = "Select role"
                !acceptedTerms -> {
                    Toast.makeText(this, "You must accept terms and conditions", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                !isEmailValid(email) -> return@setOnClickListener
                !isPasswordValid(password) -> return@setOnClickListener
                password != confirmPassword -> {
                    binding.etConfirmPassword.error = "Passwords don't match"
                    return@setOnClickListener
                }
                !isRoleValid(role) -> return@setOnClickListener
                else -> viewModel.register(name, email, password, role)
            }
        }

        binding.btnLogin.setOnClickListener {
            finish()
        }

        viewModel.registerResponse.observe(this) { result ->
            result.onSuccess {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                finish()
            }.onFailure {
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun isPasswordValid(password: String): Boolean {
        return when {
            password.length < 8 -> {
                binding.etPassword.error = "Password must be at least 8 characters"
                false
            }
            !password.matches(Regex(".*[A-Z].*")) -> {
                binding.etPassword.error = "Must contain at least one uppercase letter"
                false
            }
            !password.matches(Regex(".*[a-z].*")) -> {
                binding.etPassword.error = "Must contain at least one lowercase letter"
                false
            }
            !password.matches(Regex(".*\\d.*")) -> {
                binding.etPassword.error = "Must contain at least one digit"
                false
            }
            !password.matches(Regex(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) -> {
                binding.etPassword.error = "Must contain at least one special character"
                false
            }
            else -> true
        }
    }
    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches().also { isValid ->
            if (!isValid) binding.etEmail.error = "Invalid email format"
        }
    }
    private fun isRoleValid(role: String): Boolean {
        return when (role) {
            "admin", "staff" -> true
            else -> {
                binding.etRole.error = "Role must be either Admin or Staff"
                false
            }
        }
    }

}