package com.swjsw.example.protodatastore.data.repository.user

import android.util.Log
import androidx.datastore.core.DataStore
import com.swjsw.example.protodatastore.data.repository.user.UserDefault.DEFAULT_USER_NAME
import com.swjsw.example.protodatastore.models.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userPreferencesStore: DataStore<UserPreferences>,
) : UserRepository {

    override val userName: Flow<String> = userPreferencesStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(UserPreferences.getDefaultInstance())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            Log.d("datastore", "flowtest UserRepositoryImpl : userName : ${preferences.name}")
            if (preferences.name.isNullOrEmpty()) {
                DEFAULT_USER_NAME
            } else preferences.name
        }
//            .flowOn(Dispatchers.IO) // datastore 는 기보적으로 IO 스레드에서 실행되지만 여기서는 명시적으로 지정, 없어도 무방함

    override suspend fun getUserName(): String {
        return userName.first()
    }

    override suspend fun setUserName(name: String) {
        userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setName(name).build()
        }
    }

    override val userInfo: Flow<UserInfo> = userPreferencesStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(UserPreferences.getDefaultInstance())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val name = if (preferences.name.isNullOrEmpty()) {
                DEFAULT_USER_NAME
            } else preferences.name

            val email = if (preferences.email.isNullOrEmpty()) {
                ""
            } else preferences.email

            UserInfo(name, email)
        }

    override suspend fun getUserInfo(): UserInfo {
        return userInfo.first()
    }

    override suspend fun setUserInfo(userInfo: UserInfo) {
        userPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setName(userInfo.userName)
                .setEmail(userInfo.email)
                .build()
        }
    }

}