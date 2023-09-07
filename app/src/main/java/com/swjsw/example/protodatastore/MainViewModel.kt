package com.swjsw.example.protodatastore

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swjsw.example.protodatastore.data.repository.user.UserPreferences
import com.swjsw.example.protodatastore.data.repository.user.UserRepository
import com.swjsw.example.protodatastore.models.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userNameForSaving: MutableLiveData<String> = MutableLiveData()
    val userNameForSaving: MutableLiveData<String> get() = _userNameForSaving

    private val _userEmailForSaving: MutableLiveData<String> = MutableLiveData()
    val userEmailForSaving: MutableLiveData<String> get() = _userEmailForSaving

    private val _userName: MutableLiveData<String> = MutableLiveData()
    val userName: MutableLiveData<String> get() = _userName

    private val _userInfo: MutableLiveData<UserInfo> = MutableLiveData()
    val userInfo: MutableLiveData<UserInfo> get() = _userInfo

    init {
        initSubscribe()
    }

    private fun initSubscribe() {
        viewModelScope.launch {
            userRepository.userName.collect {
                Log.d("MainViewModel", "initSubscribe: $it")
                setUserName(it)
            }
        }

        viewModelScope.launch {
            userRepository.userInfo.collect {
                Log.d("MainViewModel", "initSubscribe: $it")
                setUserInfo(it)
            }
        }
    }

    private suspend fun getUserNameFromRepository(): String {
        return userRepository.getUserName()
    }

    fun setUserNameToRepository() {
        viewModelScope.launch {
            userRepository.setUserName(userNameForSaving.value ?: "")
        }
    }

    private fun setUserName(userName: String) {
        _userName.value = userName
    }

    fun setUserNameForSaving(userName: String) {
        _userNameForSaving.value = userName
    }

    fun setUserEmailForSaving(userEmail: String) {
        _userEmailForSaving.value = userEmail
    }

    private suspend fun getUserInfoFromRepository(): UserInfo {
        return userRepository.getUserInfo()
    }

    fun setUserInfoToRepository() {
        viewModelScope.launch {
            userRepository.setUserInfo(UserInfo(userNameForSaving.value ?: "", userEmailForSaving.value ?: ""))
        }
    }

    private fun setUserInfo(userInfo: UserInfo) {
        _userInfo.value = userInfo
    }

}