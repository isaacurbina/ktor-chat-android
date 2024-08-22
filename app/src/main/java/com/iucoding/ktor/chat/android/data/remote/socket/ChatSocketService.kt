package com.iucoding.ktor.chat.android.data.remote.socket

import com.iucoding.ktor.chat.android.domain.model.Message
import com.iucoding.ktor.chat.android.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {

    suspend fun initSession(username: String): Resource<Unit>

    suspend fun closeSession()

    suspend fun sendMessage(message: String)

    fun observeMessages(): Flow<Message>

    companion object {
        const val BASE_URL = "ws://localhost:8080/"
    }
}
