package com.swjsw.example.protodatastore.data.repository

import androidx.datastore.core.DataStore
import com.swjsw.example.protodatastore.data.repository.user.UserPreferences
import com.swjsw.example.protodatastore.data.repository.user.UserRepository
import com.swjsw.example.protodatastore.data.repository.user.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideUserRepository(dataStore: DataStore<UserPreferences>): UserRepository {
        return UserRepositoryImpl(dataStore)
    }
}