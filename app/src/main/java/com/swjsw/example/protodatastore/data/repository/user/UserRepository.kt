package com.swjsw.example.protodatastore.data.repository.user

import com.swjsw.example.protodatastore.models.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val userName: Flow<String>
    suspend fun getUserName(): String
    suspend fun setUserName(name: String)

    val userInfo: Flow<UserInfo>
    suspend fun getUserInfo(): UserInfo
    suspend fun setUserInfo(userInfo: UserInfo)
}