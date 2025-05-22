package com.rkdigital.stocklytics.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkdigital.stocklytics.model.LoginResponse
import com.rkdigital.stocklytics.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository): ViewModel() {

    private val _loginResponse = MutableLiveData<Result<LoginResponse>>();
    val loginResponse : LiveData<Result<LoginResponse>> = _loginResponse

    private val _registerResponse = MutableLiveData<Result<Unit>>()
    val registerResponse: LiveData<Result<Unit>> = _registerResponse

    private val _tokenValidationResult = MutableLiveData<Result<Unit>>()
    val tokenValidationResult: LiveData<Result<Unit>> get() = _tokenValidationResult


    fun login (email:String ,password:String){
        viewModelScope.launch {
            try {
                val response = authRepository.login(email, password)
                if (response.isSuccessful && response.body() != null) {
                    print(response.body())
                    _loginResponse.value = Result.success(response.body()!!)
                } else {
                    _loginResponse.value = Result.failure(Exception("Invalid credentials"))
                }
            } catch (e: Exception) {
                _loginResponse.value = Result.failure(e)
            }
        }
    }
    fun register(name: String, email: String, password: String, role: String) {
        viewModelScope.launch {
            val result = authRepository.register(name, email, password, role)
            _registerResponse.postValue(result)
        }
    }
    fun validateToken(token: String) {
        viewModelScope.launch {
            val result = authRepository.validateToken(token)
            _tokenValidationResult.postValue(result)
        }
    }


}