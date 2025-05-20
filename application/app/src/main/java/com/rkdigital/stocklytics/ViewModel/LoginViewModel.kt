package com.rkdigital.stocklytics.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkdigital.stocklytics.Model.LoginResponse
import com.rkdigital.stocklytics.Repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val authRepository = AuthRepository();
    private val _loginResponse = MutableLiveData<Result<LoginResponse>>();
    val loginResponse : LiveData<Result<LoginResponse>> = _loginResponse
    fun login (email:String ,password:String){
        viewModelScope.launch {
            try {
                val response = authRepository.login(email, password)
                if (response.isSuccessful && response.body() != null) {
                    _loginResponse.value = Result.success(response.body()!!)
                } else {
                    _loginResponse.value = Result.failure(Exception("Invalid credentials"))
                }
            } catch (e: Exception) {
                _loginResponse.value = Result.failure(e)
            }
        }
    }
}